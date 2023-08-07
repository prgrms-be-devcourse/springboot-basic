package com.devcourse.voucher.domain.repository;

import com.devcourse.global.sql.Delete;
import com.devcourse.global.sql.Insert;
import com.devcourse.global.sql.Select;
import com.devcourse.global.sql.Update;
import com.devcourse.global.sql.Where;
import com.devcourse.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
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
        Insert insert = Insert.builder()
                .into(Voucher.class)
                .values("id", "discount", "expired_at", "type", "status")
                .build();

        jdbcTemplate.update(insert.getQuery(),
                voucher.id().toString(),
                voucher.discount(),
                voucher.expireAt(),
                voucher.type().name(),
                voucher.status().name());

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        Select select = Select.builder()
                .select(Voucher.class)
                .build();

        return jdbcTemplate.query(select.getQuery(), voucherMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        Select select = Select.builder()
                .select(Voucher.class)
                .where(
                        Where
                                .builder()
                                .condition("id")
                                .build()
                ).build();

        return jdbcTemplate.query(select.getQuery(), voucherMapper, id.toString())
                .stream()
                .findFirst();
    }

    @Override
    public void deleteById(UUID id) {
        Delete delete = Delete.builder()
                .from(Voucher.class)
                .where(
                        Where
                                .builder()
                                .condition("id")
                                .build()
                ).build();

        jdbcTemplate.update(delete.getQuery(), id.toString());
    }

    @Override
    public void updateStatus(UUID id, String status) {
        Update update = Update.builder()
                .table(Voucher.class)
                .values("status")
                .where(
                        Where.builder()
                                .condition("id")
                                .build()
                ).build();

        jdbcTemplate.update(update.getQuery(), status, id.toString());
    }
}
