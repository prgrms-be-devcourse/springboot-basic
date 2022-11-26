package com.programmers.commandline.domain.voucher.repository.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import com.programmers.commandline.global.config.MyDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile("jdbc")
public class VoucherNamedJdbcRepository implements VoucherRepository {

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID id = UUID.fromString(resultSet.getString("id"));
        VoucherType type = VoucherType.valueOf(resultSet.getString("type"));
        long discount = Long.parseLong(resultSet.getString("discount"));
        LocalDateTime createdAt = LocalDateTime.parse(resultSet.getString("created_at"));

        return type.createVoucher(id, discount, createdAt);
    };

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    Logger logger = LoggerFactory.getLogger(VoucherNamedJdbcRepository.class);

    public VoucherNamedJdbcRepository(MyDataSource myDataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(myDataSource.getDataSource());
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("id", voucher.getId());
            put("type", voucher.getType());
            put("discount", voucher.getDiscount());
            put("createdAt", voucher.getCreatedAt());
        }};
    }

    @Override
    public Voucher insert(Voucher voucher) {
        String sql = "INSERT INTO voucher(id, type, discount, created_at) " +
                "VALUES (:id, :type, :discount, :createdAt)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", voucher.getId())
                .addValue("type", voucher.getType())
                .addValue("discount", voucher.getDiscount())
                .addValue("createdAt", voucher.getCreatedAt());

        int update = namedParameterJdbcTemplate.update(sql, sqlParameterSource);

        if (update != 1) {
            logger.error("Noting was inserted");
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        String sql = "UPDATE voucher SET type = :type, discount = :discount WHERE id = :id";
        int update = namedParameterJdbcTemplate.update(sql, toParamMap(voucher));

        if (update != 1) {
            logger.error("Noting was inserted");
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public int count() {
        String sql = "select count(*) from voucher";
        return namedParameterJdbcTemplate.queryForObject(sql, Collections.emptyMap(), Integer.class);
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from voucher";
        return namedParameterJdbcTemplate.query(sql, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(String voucherId) {
        try {
            String sql = "select * from voucher WHERE id = :id";
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    sql,
                    Collections.singletonMap("id", voucherId),
                    voucherRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM voucher";
        namedParameterJdbcTemplate.update(sql, Collections.emptyMap());
    }
}
