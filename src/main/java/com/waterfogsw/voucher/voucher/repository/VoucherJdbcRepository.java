package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile({"jdbc"})
@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        final var id = resultSet.getLong("voucher_id");
        final var type = VoucherType.valueOf(resultSet.getString("voucher_type"));
        final var value = resultSet.getInt("value");
        final var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        final var updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return Voucher.of(id, type, value, createdAt, updatedAt);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("id", voucher.getId());
            put("type", voucher.getType().name());
            put("value", voucher.getValue());
            put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
            put("updatedAt", Timestamp.valueOf(voucher.getUpdatedAt()));
        }};
    }

    @Override
    public Voucher save(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException();
        }

        if (voucher.getId() == null) {
            final var updatedNum = jdbcTemplate.update(
                    "INSERT INTO vouchers(voucher_type, value, created_at, updated_at) VALUES (:type, :value, :createdAt, :updatedAt)",
                    toParamMap(voucher)
            );
            if (updatedNum != 1) {
                throw new IllegalStateException("Nothing was inserted");
            }

            final var id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Collections.emptyMap(), Long.class);

            return Voucher.toEntity(id, voucher);
        }

        final var updatedNum = jdbcTemplate.update(
                "UPDATE vouchers SET voucher_type = :type, value = :value, updated_at = :updatedAt WHERE voucher_id = :id",
                toParamMap(voucher)
        );

        if (updatedNum != 1) {
            throw new IllegalStateException("Error occur while update");
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }
}
