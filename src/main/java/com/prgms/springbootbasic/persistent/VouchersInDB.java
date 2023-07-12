package com.prgms.springbootbasic.persistent;

import com.prgms.springbootbasic.domain.Voucher;
import com.prgms.springbootbasic.util.BinaryToUUID;
import com.prgms.springbootbasic.util.ExceptionMessage;
import com.prgms.springbootbasic.util.VoucherType;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

@Repository
@Primary
public class VouchersInDB implements VouchersStorage {

    private static final String INSERT = "insert into vouchers(voucher_id, type, amount) values (UUID_TO_BIN(:voucherId), :type, :amount)";
    private static final String FIND_ALL = "select * from vouchers";
    private static final String FIND_ONE = "select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String DELETE_ONE = "delete from vouchers where voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String UPDATE_AMOUNT = "update vouchers set amount = :amount where voucher_id = UUID_TO_BIN(:voucherId)";

    private final RowMapper<Voucher> voucherMapper = (ResultSet resultSet, int i) -> {
        UUID voucherId = BinaryToUUID.biToUUID(resultSet.getBytes("voucher_id"));
        int amount = resultSet.getInt("amount");
        String typeOfString = resultSet.getString("type");
        VoucherType voucherType = VoucherType.of(typeOfString);
        return voucherType.createVoucher(voucherId, amount);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VouchersInDB(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Voucher voucher) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("voucherId", voucher.getVoucherId().toString().getBytes())
                .addValue("amount", voucher.getAmount())
                .addValue("type", voucher.getVoucherType().getType());
        int result = jdbcTemplate.update(INSERT, sqlParameterSource);
        if (result != 1) {
            throw new IllegalStateException(ExceptionMessage.FAIL_TO_INSERT.getMessage());
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL, voucherMapper);
    }

    @Override
    public Voucher findById(UUID voucherId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("voucherId", voucherId.toString().getBytes());
        return jdbcTemplate.queryForObject(FIND_ONE, sqlParameterSource, voucherMapper);
    }

    @Override
    public void update(Voucher voucher) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("voucherId", voucher.getVoucherId().toString().getBytes())
                                                            .addValue("amount", voucher.getAmount());
        int result = jdbcTemplate.update(UPDATE_AMOUNT, sqlParameterSource);
        if (result != 1) {
            throw new IllegalStateException(ExceptionMessage.FAIL_TO_UPDATE.getMessage());
        }
    }

    @Override
    public void deleteOne(UUID voucherId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("voucherId", voucherId.toString().getBytes());
        int result = jdbcTemplate.update(DELETE_ONE, sqlParameterSource);
        if (result != 1) {
            throw new IllegalStateException(ExceptionMessage.FAIL_TO_DELETE.getMessage());
        }
    }

}
