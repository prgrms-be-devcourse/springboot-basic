package com.devcourse.voucher.domain.repository;

import com.devcourse.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Profile("dev")
class JdbcVoucherRepository implements VoucherRepository {
    private static final RowMapper<Voucher> voucherMapper = (resultSet, resultNumber) -> {
        UUID id = UUID.fromString(resultSet.getString("id"));
        int discount = Integer.parseInt(resultSet.getString("discount"));
        LocalDateTime expiredAt = resultSet.getTimestamp("expired_at").toLocalDateTime();
        Voucher.Type type = Enum.valueOf(Voucher.Type.class, resultSet.getString("type"));
        Voucher.Status status = Enum.valueOf(Voucher.Status.class, resultSet.getString("status"));
        return new Voucher(id, discount, expiredAt, type, status);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        jdbcTemplate.update("INSERT INTO vouchers(id, discount, expired_at, type, status) VALUES (?, ?, ?, ?, ?)",
                voucher.id(),
                voucher.discount(),
                voucher.expireAt(),
                voucher.type().name(),
                voucher.status().name());

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE id = ?",
                    voucherMapper,
                    id.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
