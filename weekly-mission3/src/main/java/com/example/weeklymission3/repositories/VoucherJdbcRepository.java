package com.example.weeklymission3.repositories;

import com.example.weeklymission3.models.Voucher;
import com.example.weeklymission3.models.VoucherType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.weeklymission3.utils.JdbcUtils.toUUID;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int update = jdbcTemplate.update(
                "INSERT INTO vouchers(voucher_id, type, discount, created_at) VALUES (UUID_TO_BIN(:voucherId), :type, :discount, :createdAt)",
                toParamMap(voucher)
        );
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted.");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByTime(LocalDateTime startTime, LocalDateTime endTime) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        return jdbcTemplate.query("SELECT * FROM vouchers WHERE created_at BETWEEN :startTime AND :endTime",
                paramMap,
                voucherRowMapper);
    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        return jdbcTemplate.query("SELECT * FROM vouchers WHERE type = :type",
                Collections.singletonMap("type", type.toString()),
                voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        paramMap.put("type", voucher.getType());
        paramMap.put("discount", voucher.getDiscount());
        paramMap.put("createdAt", LocalDateTime.now());
        return paramMap;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String type = resultSet.getString("type");
        int discount = resultSet.getInt("discount");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Voucher(voucherId, type, discount, createdAt);
    };
}
