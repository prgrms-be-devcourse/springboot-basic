package org.prgms.management.voucher.repository;

import org.prgms.management.voucher.entity.Voucher;
import org.prgms.management.voucher.entity.VoucherType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class VoucherJdbcRepository implements VoucherRepository {
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
        return VoucherType.createVoucher(
                voucherId, discountNum, name, type, createdAt).orElse(null);
    };

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        var executeUpdate = jdbcTemplate.update("INSERT INTO vouchers(" +
                        "voucher_id, name, type, discount_num, created_at) " +
                        "VALUES (UUID_TO_BIN(:voucherId), :name, :type, :discountNum, :createdAt)",
                toParamMap(voucher));

        if (executeUpdate != 1) {
            return Optional.empty();
        }

        return Optional.of(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", rowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM vouchers WHERE voucher_id = :voucherId",
                Collections.singletonMap("voucherId", voucherId),
                rowMapper));
    }

    @Override
    public Optional<Voucher> findByCreatedAt(LocalDateTime createdAt) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM vouchers WHERE created_at = :createdAt",
                Collections.singletonMap("createdAt", createdAt),
                rowMapper));
    }

    @Override
    public Optional<Voucher> findByType(String type) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM vouchers WHERE type = :type",
                Collections.singletonMap("type", type),
                rowMapper));
    }

    @Override
    public Optional<Voucher> update(Voucher voucher) {
        var executeUpdate = jdbcTemplate.update(
                "UPDATE vouchers SET name = :name, " +
                        "type = :type, discount_num = :discountNum " +
                        "WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucher));

        if (executeUpdate != 1) {
            return Optional.empty();
        }

        return Optional.of(voucher);
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
        map.put("type", voucher.getVoucherType());
        map.put("name", voucher.getVoucherName());
        map.put("discountNum", voucher.getDiscountNum());
        map.put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
        return map;
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
