package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Discount;
import com.programmers.voucher.domain.DiscountType;
import com.programmers.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Profile("jdbc")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final String NOT_FOUND_ERROR_MESSAGE = "[ERROR] 해당 요청에 대한 결과를 찾을 수 없습니다.";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into voucher (id, type, discount_value, created_at, expired_at)" +
                " values(:id, :type, :discountValue, :createdAt, :expiredAt)";
        jdbcTemplate.update(sql, converParameterToMap(voucher));
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from voucher";
        return jdbcTemplate.query(sql, voucherRowMapper());
    }

    @Override
    public Voucher findById(UUID voucherId) {
        String sql = "select * from voucher where id = :voucherId";
        try {
            return jdbcTemplate.queryForObject(sql,
                    Collections.singletonMap("voucherId", voucherId.toString()),
                    voucherRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException(NOT_FOUND_ERROR_MESSAGE);
        }
    }

    private Map<String, Object> converParameterToMap (Voucher voucher) {
        return new HashMap<>() {{
            put("id", voucher.getVoucherId().toString());
            put("type", voucher.getDiscount().getDiscountType().toString());
            put("discountValue", voucher.getDiscount().getAmount());
            put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
            put("expiredAt", Timestamp.valueOf(voucher.getExpiredAt()));
        }};
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherId = UUID.fromString(rs.getString("id"));
            DiscountType discountType = DiscountType.valueOf(rs.getString("type"));
            long discountValue = rs.getLong("discount_value");
            Discount discount = Discount.of(discountType, discountValue);
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime expiredAt = rs.getTimestamp("expired_at").toLocalDateTime();
            return new Voucher(voucherId, discount, createdAt, expiredAt);
        };
    }
}
