package org.programmers.springboot.basic.domain.voucher.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.service.validate.VoucherValidationStrategy;
import org.programmers.springboot.basic.util.converter.UUIDConverter;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcVoucherRepository implements VoucherRepository {
    private final JdbcTemplate jdbcTemplate;
    private final Map<VoucherType, VoucherValidationStrategy> validationStrategyMap;

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherId = UUIDConverter.toUUID(rs.getBytes("voucher_id"));
            VoucherType voucherType = VoucherType.valueOf(rs.getInt("voucher_type"));
            Long discount = rs.getLong("discount");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime createdAt = LocalDateTime.parse(rs.getString("createdAt"), formatter);
            return new Voucher(voucherId, voucherType, discount, createdAt, validationStrategyMap.get(voucherType));
        };
    }

    @Override
    public void save(final Voucher voucher) {
        String sql = "INSERT INTO vouchers(voucher_id, voucher_type, discount) VALUES(UUID_TO_BIN(?), ?, ?)";
        this.jdbcTemplate.update(sql,
                UUIDConverter.toBytes(voucher.getVoucherId()),
                voucher.getVoucherTypeValue(),
                voucher.getDiscount());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)";
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(sql, voucherRowMapper(),
                    (Object) UUIDConverter.toBytes(voucherId)));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No voucher found for voucherId: {}", voucherId);
        } catch (DataAccessException e) {
            log.error("Data access exception: {}", e.toString());
        }
        return Optional.empty();
    }

    @Override
    public List<Voucher> find(UUID voucherId, VoucherType voucherType, Long discount, LocalDateTime createdAt) {
        String sql = "SELECT * FROM vouchers WHERE " +
                "(voucher_id = UUID_TO_BIN(?) OR UUID_TO_BIN(?) IS NULL) " +
                "AND (voucher_type = ? OR ? IS NULL) " +
                "AND (discount = ? OR ? IS NULL) " +
                "AND (createdAt = ? OR ? IS NULL)";

        byte[] bytes = getNullableBytes(voucherId);
        Integer type = getNullableValue(voucherType);
        Timestamp timestamp = getNullableTimestamp(createdAt);
        return this.jdbcTemplate.query(sql, voucherRowMapper(),
                bytes, bytes,
                type, type,
                discount, discount,
                timestamp, timestamp);
    }

    private byte[] getNullableBytes(UUID value) {
        return value != null ? UUIDConverter.toBytes(value) : null;
    }

    private Integer getNullableValue(VoucherType value) {
        return value != null ? value.getValue() : null;
    }

    private Timestamp getNullableTimestamp(LocalDateTime value) {
        return value != null ? Timestamp.valueOf(value) : null;
    }

    @Override
    public Optional<Voucher> findByTypeNDiscount(VoucherType voucherType, Long discount) {
        String sql = "SELECT * FROM vouchers WHERE voucher_type = ? AND discount = ?";
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(sql, voucherRowMapper(),
                    voucherType.getValue(), discount));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No voucher found for voucherType {} and discount {}", voucherType, discount);
        } catch (DataAccessException e) {
            log.error("Data access exception: {}", e.toString());
        }
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT * FROM vouchers ORDER BY voucher_id";
        return this.jdbcTemplate.query(sql, voucherRowMapper());
    }

    @Override
    public void update(Voucher voucher) {
        String sql = "UPDATE vouchers SET discount = ? WHERE voucher_id = UUID_TO_BIN(?)";
        this.jdbcTemplate.update(sql, voucher.getDiscount(), UUIDConverter.toBytes(voucher.getVoucherId()));
    }

    @Override
    public void delete(UUID voucherId) {
        String sql = "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)";
        this.jdbcTemplate.update(sql, (Object) UUIDConverter.toBytes(voucherId));
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM vouchers";
        this.jdbcTemplate.update(sql);
    }
}
