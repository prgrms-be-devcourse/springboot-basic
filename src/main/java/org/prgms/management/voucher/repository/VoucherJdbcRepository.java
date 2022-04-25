package org.prgms.management.voucher.repository;

import org.prgms.management.voucher.entity.Voucher;
import org.prgms.management.voucher.entity.VoucherCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"local-db", "dev"})
public class VoucherJdbcRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Voucher> rowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var name = resultSet.getString("name");
        var type = resultSet.getString("type");
        var discountNum = resultSet.getInt("discount_num");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return VoucherCreator.createVoucher(
                voucherId, discountNum, name, type, createdAt).orElse(null);
    };

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        try {
            var executeUpdate = jdbcTemplate.update("INSERT INTO vouchers(" +
                            "voucher_id, name, type, discount_num, created_at) " +
                            "VALUES (UUID_TO_BIN(:voucherId), :name, :type, :discountNum, :createdAt)",
                    toParamMap(voucher));

            if (executeUpdate != 1) {
                return Optional.empty();
            }

            return Optional.of(voucher);
        } catch (DuplicateKeyException e) {
            logger.error("Failed insert", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM vouchers", rowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return List.of();
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    rowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCreatedAt(LocalDateTime createdAt) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM vouchers WHERE created_at = :createdAt",
                    Collections.singletonMap("createdAt", createdAt),
                    rowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return List.of();
        }
    }

    @Override
    public List<Voucher> findByType(String type) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM vouchers WHERE type = :type",
                    Collections.singletonMap("type", type),
                    rowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return List.of();
        }
    }

    @Override
    public Optional<Voucher> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM vouchers WHERE name = :name",
                    Collections.singletonMap("name", name),
                    rowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Voucher> update(Voucher voucher) {
        try {
            var executeUpdate = jdbcTemplate.update(
                    "UPDATE vouchers SET name = :name, " +
                            "type = :type, discount_num = :discountNum " +
                            "WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    toParamMap(voucher));

            if (executeUpdate != 1) {
                return Optional.empty();
            }

            return Optional.of(voucher);
        } catch (DuplicateKeyException e) {
            logger.error("Failed update", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Voucher> delete(Voucher voucher) {
        var executeUpdate = jdbcTemplate.update(
                "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucher));

        if (executeUpdate != 1) {
            return Optional.empty();
        }

        return Optional.of(voucher);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        Map<String, Object> map = new HashMap<>();
        map.put("voucherId", voucher.getVoucherId().toString().getBytes());
        map.put("type", voucher.getType());
        map.put("name", voucher.getName());
        map.put("discountNum", voucher.getDiscountNum());
        map.put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
        return map;
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
