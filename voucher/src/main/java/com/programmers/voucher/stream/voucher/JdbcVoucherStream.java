package com.programmers.voucher.stream.voucher;

import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Profile("jdbc")
@Repository
public class JdbcVoucherStream implements VoucherStream{

    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherStream(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        saveFixedAmountVoucher(voucher, paramMap);
        savePercentDiscountVoucher(voucher, paramMap);
        return voucher;
    }

    private void saveFixedAmountVoucher(Voucher voucher, Map<String, Object> paramMap) {
        if (voucher instanceof FixedAmountVoucher) {
            paramMap.put("voucherId", voucher.getVoucherId());
            paramMap.put("amount", ((FixedAmountVoucher) voucher).getAmount());
            paramMap.put("rate", null);
            jdbcTemplate.update("INSERT INTO vouchers(voucher_id, type, amount, rate)  VALUES (:voucherId,'FixedAmountVoucher',:amount,:rate)",
                    paramMap);
        }
    }

    private void savePercentDiscountVoucher(Voucher voucher, Map<String, Object> paramMap) {
        if (voucher instanceof PercentDiscountVoucher) {
            paramMap.put("voucherId", voucher.getVoucherId());
            paramMap.put("amount", null);
            paramMap.put("rate", ((PercentDiscountVoucher) voucher).getPercent());
            jdbcTemplate.update("INSERT INTO vouchers(voucher_id, type, amount, rate)  VALUES (:voucherId,'PercentDiscountVoucher',:amount,:rate)",
                    paramMap);
        }
    }

    public Voucher findById(String voucherId) {
        return jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = :voucherId",
                Collections.singletonMap("voucherId", voucherId),
                (resultSet, i) -> voucherRowMapper(resultSet));
    }

    @Override
    public Map<String, Voucher> findAll() {
        Map<String, Voucher> voucherMap = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM vouchers", (resultSet, i) -> voucherRowMapper(resultSet))
                .forEach(v -> voucherMap.put(v.getVoucherId(), v));
        return voucherMap;
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    public Voucher update(Voucher voucher) {
        updateFixedAmountVoucher(voucher);
        updatePercentDiscountVoucher(voucher);
        return voucher;
    }

    private void updateFixedAmountVoucher(Voucher voucher) {
        if (voucher instanceof FixedAmountVoucher) {
            var paramMap = new HashMap<String, Object>(){{
                put("voucherId", voucher.getVoucherId());
                put("amount", ((FixedAmountVoucher) voucher).getAmount());
            }};
            jdbcTemplate.update("UPDATE vouchers SET amount = :amount WHERE voucher_id = :voucherId",
                    paramMap);
        }
    }

    private void updatePercentDiscountVoucher(Voucher voucher) {
        if (voucher instanceof PercentDiscountVoucher) {
            var paramMap = new HashMap<String, Object>(){{
                put("voucherId", voucher.getVoucherId());
                put("rate", ((PercentDiscountVoucher) voucher).getPercent());
            }};
            jdbcTemplate.update("UPDATE vouchers SET amount = :amount WHERE voucher_id = :voucherId",
                    paramMap);
        }
    }


    private static Voucher voucherRowMapper(ResultSet resultSet) throws SQLException {
        String voucherId = resultSet.getString("voucher_id");
        Integer amount = resultSet.getInt("amount");
        Integer rate = resultSet.getInt("rate");

        if (amount != 0) {
            return new FixedAmountVoucher(voucherId, amount);
        }
        if (rate != 0) {
            return new PercentDiscountVoucher(voucherId, rate);
        }
        return null;
    }

}
