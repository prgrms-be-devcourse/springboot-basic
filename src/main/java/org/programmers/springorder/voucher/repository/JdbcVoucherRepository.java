package org.programmers.springorder.voucher.repository;

import org.programmers.springorder.voucher.model.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Primary
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String INSERT_QUERY = "insert into vouchers(voucher_id, discount_value, voucher_type) values(UUID_TO_BIN(:voucherId), :discountValue, :voucherType)";
    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        int update = jdbcTemplate.update(INSERT_QUERY, toParamMap(voucher));
        if( update != 1){
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("discountValue", voucher.getDiscountValue());
            put("voucherType", voucher.getVoucherType().name());
        }};
    }
}
