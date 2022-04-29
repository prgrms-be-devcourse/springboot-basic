package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return Voucher.toEntity(id, Voucher.of(type, value));
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
        }};
    }

    @Override
    public Voucher save(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException();
        }

        if (voucher.getId() == null) {
            final var update = jdbcTemplate.update(
                    "INSERT INTO vouchers(voucher_type, value) VALUES (:type, :value)",
                    toParamMap(voucher)
            );
            if (update != 1) {
                throw new RuntimeException("Nothing was inserted");
            }

            final var id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Collections.emptyMap(), Long.class);

            return Voucher.toEntity(id, voucher);
        }

        final var update = jdbcTemplate.update(
                "UPDATE voucher_mgmt SET voucher_type = :type, value = :value WHERE id = :id",
                toParamMap(voucher)
        );

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }
}
