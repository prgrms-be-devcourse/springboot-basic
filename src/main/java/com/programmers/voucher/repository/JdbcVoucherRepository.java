package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Profile("jdbc")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private Map<String, Object> converParameterToMap (Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString());
            put("type", voucher.getDiscount().getDiscountType().toString());
            put("discountValue", voucher.getDiscount().getAmount());
            put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
            put("expiredAt", Timestamp.valueOf(voucher.getExpiredAt()));
        }};
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into voucher (id, type, discount_value, created_at, expired_at)" +
                " values(:voucherId, :type, :discountValue, :createdAt, :expiredAt)";
        jdbcTemplate.update(sql, converParameterToMap(voucher));
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        return null;
    }
}
