package com.programmers.voucher.stream.voucher;

import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JdbcVoucherStream implements VoucherStream{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherStream(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(voucher);
        saveFixedAmountVoucher(voucher, param);
        savePercentDiscountVoucher(voucher, param);
        return voucher;
    }

    private void saveFixedAmountVoucher(Voucher voucher, SqlParameterSource param) {
        if (voucher instanceof FixedAmountVoucher) {
            jdbcTemplate.update("INSERT INTO vouchers(voucher_id, type, amount)  VALUES (:voucherId,'FixedAmountVoucher',:amount)",
                    param);
        }
    }

    private void savePercentDiscountVoucher(Voucher voucher, SqlParameterSource param) {
        if (voucher instanceof PercentDiscountVoucher) {
            jdbcTemplate.update("INSERT INTO vouchers(voucher_id, type, rate)  VALUES (:voucherId,'PercentDiscountVoucher',:rate)",
                    param);
        }
    }

    @Override
    public Voucher findById(String voucherId) {
        return jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = :voucherId",
                Map.of("voucherId", voucherId),
                (resultSet, i) -> voucherRowMapper(resultSet));
    }

    @Override
    public Map<String, Voucher> findAll() {
        Map<String, Voucher> voucherMap = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM vouchers", (resultSet, i) -> voucherRowMapper(resultSet))
                .forEach(v -> voucherMap.put(v.getVoucherId(), v));
        return voucherMap;
    }

    @Override
    public void deleteById(String voucherId) {
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = :voucherId"
                , Map.of("voucherId", voucherId));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Map.of());
    }

    @Override
    public Voucher update(Voucher voucher) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(voucher);
        updateFixedAmountVoucher(voucher, param);
        updatePercentDiscountVoucher(voucher, param);
        return voucher;
    }

    private void updateFixedAmountVoucher(Voucher voucher, SqlParameterSource param) {
        if (voucher instanceof FixedAmountVoucher) {
            jdbcTemplate.update("UPDATE vouchers SET amount = :amount WHERE voucher_id = :voucherId",
                    param);

        }
    }

    private void updatePercentDiscountVoucher(Voucher voucher, SqlParameterSource param) {
        if (voucher instanceof PercentDiscountVoucher) {
            jdbcTemplate.update("UPDATE vouchers SET rate = :rate WHERE voucher_id = :voucherId",
                    param);
        }
    }

    private  Voucher voucherRowMapper(ResultSet resultSet) throws SQLException {
        String voucherId = resultSet.getString("voucher_id");
        String voucherType = resultSet.getString("type");
        Integer amount = resultSet.getInt("amount");
        Integer rate = resultSet.getInt("rate");
        switch (voucherType) {
            case "FixedAmountVoucher" ->{
                return new FixedAmountVoucher(voucherId, amount);
            }
            case "PercentDiscountVoucher" -> {
                return new PercentDiscountVoucher(voucherId, rate);
            }
            default -> throw new IllegalStateException("만족하지 않는 데이터가 있습니다.");
        }
    }

}
