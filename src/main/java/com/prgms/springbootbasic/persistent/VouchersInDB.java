package com.prgms.springbootbasic.persistent;

import com.prgms.springbootbasic.domain.Voucher;
import com.prgms.springbootbasic.util.SQLQuery;
import com.prgms.springbootbasic.util.VoucherType;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

@Repository
@Primary
public class VouchersInDB implements VouchersStorage {

    private static RowMapper<Voucher> voucherMapper = (ResultSet resultSet, int i) -> {
        UUID voucherId = biToUUID(resultSet.getBytes("voucher_id"));
        int amount = resultSet.getInt("amount");
        String typeOfString = resultSet.getString("type");
        VoucherType voucherType = VoucherType.of(typeOfString);
        return voucherType.exsistVoucher(voucherId, amount);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VouchersInDB(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Voucher voucher) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("voucherId", voucher.getVoucherId().toString().getBytes())
                                                                        .addValue("amount", voucher.getNumber())
                                                                        .addValue("type", voucher.getVoucherType().getType());
        int result = jdbcTemplate.update(SQLQuery.INSERT_VOUCHER.getQuery(), sqlParameterSource);
        if (result != 1) throw new IllegalStateException();
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SQLQuery.FIND_ALL_VOUCHER.getQuery(), voucherMapper);
    }

    @Override
    public Voucher findById(UUID voucherId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("voucherId", voucherId.toString().getBytes());
        return jdbcTemplate.queryForObject(SQLQuery.FIND_ONE_VOUCHER.getQuery(), sqlParameterSource, voucherMapper);
    }

    @Override
    public void update(Voucher voucher) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("voucherId", voucher.getVoucherId().toString().getBytes())
                                                            .addValue("amount", voucher.getNumber());
        int result = jdbcTemplate.update(SQLQuery.UPDATE_VOUCHER.getQuery(), sqlParameterSource);
        if (result != 1) throw new IllegalStateException();
    }

    @Override
    public void deleteOne(UUID voucherId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("voucherId", voucherId.toString().getBytes());
        int result = jdbcTemplate.update(SQLQuery.DELETE_VOUCHER.getQuery(), sqlParameterSource);
        if (result != 1) throw new IllegalStateException();
    }

    static UUID biToUUID(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }

}
