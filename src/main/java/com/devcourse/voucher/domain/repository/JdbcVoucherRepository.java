package com.devcourse.voucher.domain.repository;

import com.devcourse.global.util.Sql;
import com.devcourse.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.devcourse.global.util.Sql.Table.VOUCHERS;

@Component
@Profile("dev")
class JdbcVoucherRepository implements VoucherRepository {
    private final RowMapper<Voucher> voucherMapper = (resultSet, resultNumber) -> {
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
        String sql = Sql.builder()
                .insertInto(VOUCHERS)
                .values("id", "discount", "expired_at", "type", "status")
                .build();

        jdbcTemplate.update(sql,
                voucher.id(),
                voucher.discount(),
                voucher.expireAt(),
                voucher.type().name(),
                voucher.status().name());

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = Sql.builder()
                .select("*")
                .from(VOUCHERS)
                .build();

        return jdbcTemplate.query(sql, voucherMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        String sql = Sql.builder()
                .select("*")
                .from(VOUCHERS)
                .where("id")
                .build();

        List<Voucher> result = jdbcTemplate.query(sql, voucherMapper, id.toString());
        Voucher voucher = DataAccessUtils.singleResult(result);

        return Optional.ofNullable(voucher);
    }

    @Override
    public void deleteById(UUID id) {
        String sql = Sql.builder()
                .deleteFrom(VOUCHERS)
                .where("id")
                .build();

        jdbcTemplate.update(sql, id.toString());
    }

    @Override
    public void updateStatus(UUID id, String status) {
        String sql = Sql.builder()
                .update(VOUCHERS)
                .set("status")
                .where("id")
                .build();

        jdbcTemplate.update(sql, status, id.toString());
    }
}
