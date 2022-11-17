package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {
    private static final String insertSql
            = "INSERT INTO vouchers(voucher_number, discount_value, voucher_type) " +
            "VALUES(UUID_TO_BIN(:voucherNumber), :discountValue, :voucherType)";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher, VoucherType voucherType) {
        jdbcTemplate.update(insertSql, toParamMap(voucher, voucherType));
        return voucher;
    }

    private Map<String, Object> toParamMap(Voucher voucher, VoucherType voucherType) {
        return Map.of(
                "voucherNumber", voucher.getVoucherNumber().toString().getBytes(),
                "discountValue", voucher.getDiscountValue(),
                "voucherType", voucherType.getVoucherType()
        );
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
