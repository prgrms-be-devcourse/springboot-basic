package org.prgrms.voucherapp.engine.voucher.repository;

import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.exception.SqlStatementFailException;
import org.prgrms.voucherapp.exception.WrongSqlValueException;
import org.prgrms.voucherapp.global.Util;
import org.prgrms.voucherapp.global.enums.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile({"db"})
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        var voucherId = Util.toUUID(resultSet.getBytes("voucher_id"));
        var amount = resultSet.getInt("amount");
        VoucherType voucherType = VoucherType.getType(resultSet.getString("type"))
                .orElseThrow(() -> (new WrongSqlValueException("알 수 없는 voucher 타입입니다.")));
        return voucherType.createVoucher(voucherId, amount);
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<String, Object>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("amount", voucher.getAmount());
            put("type", voucher.getTypeName());
        }};
    }

    public static RowMapper<Voucher> getVoucherRowMapper() {
        return voucherRowMapper;
    }

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int insert = jdbcTemplate.update("insert into vouchers(voucher_id, amount, type) values(UUID_TO_BIN(:voucherId), :amount, :type)", toParamMap(voucher));
        if (insert != 1) throw new SqlStatementFailException("정상적으로 삽입되지 않았습니다.");
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update("update vouchers set type = :type, amount = :amount where voucher_id = UUID_TO_BIN(:voucherId)", toParamMap(voucher));
        if (update != 1) throw new SqlStatementFailException("정상적으로 수정되지 않았습니다.");
        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        int delete = jdbcTemplate.update("delete from vouchers where voucher_id = UUID_TO_BIN(:voucherId)", Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
        if (delete != 1) throw new SqlStatementFailException("정상적으로 삭제되지 않았습니다.");
    }
}
