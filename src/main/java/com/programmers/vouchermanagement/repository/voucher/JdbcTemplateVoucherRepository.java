package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.domain.voucher.*;
import com.programmers.vouchermanagement.dto.voucher.request.GetVouchersRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcTemplateVoucherRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public JdbcTemplateVoucherRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public UUID save(Voucher voucher) {
        UUID id = UUID.randomUUID();
        String sql = "INSERT INTO vouchers (id, type, amount, created_at) VALUES (:id, :type, :amount, :createdAt)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", (voucher.getId() != null) ? voucher.getId().toString() : id.toString())
                .addValue("type", voucher.getType().toString())
                .addValue("amount", voucher.getAmount())
                .addValue("createdAt", (voucher.getCreatedAt() != null) ? voucher.getCreatedAt() : LocalDateTime.now());

        template.update(sql, params);

        return id;
    }

    @Override
    public void saveAll(List<Voucher> vouchers) {
        String sql = "INSERT INTO vouchers (id, type, amount, created_at) VALUES (:id, :type, :amount, :createdAt)";

        template.batchUpdate(sql, vouchers.stream()
                .map(voucher -> new MapSqlParameterSource()
                        .addValue("id", (voucher.getId() != null) ? voucher.getId().toString() : UUID.randomUUID().toString())
                        .addValue("type", voucher.getType().toString())
                        .addValue("amount", voucher.getAmount())
                        .addValue("createdAt", (voucher.getCreatedAt() != null) ? voucher.getCreatedAt() : LocalDateTime.now()))
                .toArray(SqlParameterSource[]::new));
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        String sql = "SELECT * FROM vouchers WHERE id = :id";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id.toString());

        try {
            Voucher voucher = template.queryForObject(sql, params, getVoucherRowMapper());
            return Optional.ofNullable(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll(GetVouchersRequestDto request) {
        String sql = "SELECT * FROM vouchers WHERE 1 = 1";
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (request.getType() != null) {
            sql += " AND type = :type";
            params.addValue("type", request.getType().toString());
        }

        if (request.getMinCreatedAt() != null) {
            sql += " AND created_at >= :minCreatedAt";
            params.addValue("minCreatedAt", request.getMinCreatedAt().toString());

        }

        if (request.getMaxCreatedAt() != null) {
            sql += " AND created_at <= :maxCreatedAt";
            params.addValue("maxCreatedAt", request.getMaxCreatedAt().toString());
        }

        return template.query(sql, params, getVoucherRowMapper());
    }


    @Override
    public void update(Voucher voucher) {
        String sql = "UPDATE vouchers SET amount = :amount WHERE id = :id";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", voucher.getId().toString())
                .addValue("amount", voucher.getAmount());

        template.update(sql, params);
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM vouchers WHERE id = :id";
        template.update(sql, new MapSqlParameterSource("id", id.toString()));
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM vouchers";
        template.update(sql, new MapSqlParameterSource());
    }

    private RowMapper<Voucher> getVoucherRowMapper() {
        return (rs, rowNum) -> {
            UUID id = UUID.fromString(rs.getString("id"));
            VoucherType type = VoucherType.valueOf(rs.getString("type"));
            long amount = rs.getLong("amount");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

            return VoucherFactory.create(id, type, amount, createdAt);
        };
    }
}
