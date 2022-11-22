package com.programmers.voucher.repository;

import com.programmers.voucher.domain.VoucherEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class DbVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DbVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private static final RowMapper<VoucherEntity> rowMapper = (rs, rowNum) -> {
        UUID customerId = toUUID(rs.getBytes("voucher_id"));
        String type = rs.getString("type");
        int discount = rs.getInt("discount");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime expiredAt = rs.getTimestamp("expired_at").toLocalDateTime();
        return new VoucherEntity(customerId, type, discount, createdAt, expiredAt);
    };

    @Override
    public VoucherEntity insert(VoucherEntity voucherEntity) {
        Map<String, ?> paramMap = new HashMap<>() {{
            put("voucherId", voucherEntity.getVoucherId().toString().getBytes());
            put("type", voucherEntity.getType());
            put("discount", voucherEntity.getDiscount());
            put("createdAt", Timestamp.valueOf(voucherEntity.getCreatedAt()));
            put("expiredAt", Timestamp.valueOf(voucherEntity.getExpiredAt()));
        }};
        int update = namedParameterJdbcTemplate.update("INSERT INTO voucher(voucher_id, type, discount, created_at, expired_at) VALUES(UUID_TO_BIN(:voucherId), :type, :discount, :createdAt, :expiredAt)", paramMap);
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucherEntity;
    }

    @Override
    public VoucherEntity update(VoucherEntity voucherEntity) {
        Map<String, ?> paramMap = new HashMap<>() {{
            put("voucherId", voucherEntity.getVoucherId().toString().getBytes());
            put("discount", voucherEntity.getDiscount());
        }};
        int update = namedParameterJdbcTemplate.update("UPDATE voucher SET discount= :discount WHERE voucher_id=UUID_TO_BIN(:voucherId)", paramMap);
        if (update != 1) throw new RuntimeException("Nothing was updated");
        return voucherEntity;
    }

    @Override
    public Optional<VoucherEntity> findByID(UUID voucherID) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("SELECT * FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherID.toString().getBytes()),
                    rowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<VoucherEntity> findByType(String type) {
        return namedParameterJdbcTemplate.query("SELECT * FROM voucher WHERE type= :type",Collections.singletonMap("type",type) ,rowMapper);
    }

    @Override
    public List<VoucherEntity> findAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM voucher", rowMapper);
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update("DELETE FROM voucher", Collections.emptyMap());
    }
}
