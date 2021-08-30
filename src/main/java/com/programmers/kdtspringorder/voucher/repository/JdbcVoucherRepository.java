package com.programmers.kdtspringorder.voucher.repository;

import com.programmers.kdtspringorder.voucher.VoucherType;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.factory.VoucherFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("jdbc")
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        UUID customerId = resultSet.getBytes("customer_id") != null ?
                toUUID(resultSet.getBytes("customer_id")) : null;
        VoucherType type = VoucherType.valueOf(resultSet.getString("type"));
        long value = resultSet.getLong("value");
        boolean used = resultSet.getBoolean("used");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var expirationDate = resultSet.getTimestamp("expiration_date") != null ?
                resultSet.getTimestamp("expiration_date").toLocalDateTime() : null;
        return VoucherFactory.createVoucherFromDB(type, voucherId, customerId, value, used, createdAt, expirationDate);
    };

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucherId = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                voucherRowMapper));
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public Voucher save(Voucher voucher) {
        Map<String, Object> paramMap = toParamMap(voucher);
        int update = jdbcTemplate.update("insert into vouchers(voucher_id, type, value, used, created_at, expiration_date) VALUES(UUID_TO_BIN(:voucherId), :type ,:value, :used, :createdAt, :expirationDate)",
                toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was saved");
        }
        return voucher;
    }

    @Override
    public void delete(UUID voucherId) {
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)", Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        return jdbcTemplate.query("SELECT * FROM vouchers WHERE customer_id = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                voucherRowMapper);
    }

    @Override
    public List<Voucher> findAllWithoutCustomerId() {
        return jdbcTemplate.query("SELECT * FROM vouchers WHERE customer_id IS NULL",
                Collections.emptyMap(),
                voucherRowMapper);
    }

    @Override
    public void allocateVoucher(UUID voucherId, UUID customerId) {
        Map<String, Object> paramMap = new ConcurrentHashMap<>() {{
            put("voucherId", voucherId.toString().getBytes());
            put("customerId", customerId.toString().getBytes());
        }};
        int update = jdbcTemplate.update("UPDATE vouchers SET customer_id = UUID_TO_BIN(:customerId) WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                paramMap);

        if (update != 1) {
            throw new RuntimeException("Nothing was allocated");
        }
    }

    @Override
    public void deallocateVoucher(UUID voucherId) {
        int update = jdbcTemplate.update("UPDATE vouchers SET customer_id = NULL WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
        if (update != 1) {
            throw new RuntimeException("Nothing was deallocated");
        }
    }


    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("value", voucher.getValue());
            put("type", voucher.getType().toString());
            put("used", voucher.isUsed());
            put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
            put("expirationDate", voucher.getExpirationDate() != null ? Timestamp.valueOf(voucher.getExpirationDate()) : null);
        }};
    }
}
