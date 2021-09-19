package org.programmers.voucher;

import org.programmers.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("discountValue", voucher.getDiscountValue());
            put("voucherType", voucher.getVoucherType().toString());
        }};
    }

    private Map<String, Object> toParamMap(Customer customer, Voucher voucher) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("voucherId", voucher.getVoucherId().toString().getBytes());
        }};
    }

    private RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var discountValue = resultSet.getLong("discount_value");
        var voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        var ownerId = resultSet.getBytes("owner_id") != null ? toUUID(resultSet.getBytes("owner_id")) : null;
        return new VoucherFactory().createVoucherByType(voucherType, voucherId, discountValue, ownerId);
    };

    @Override
    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, discount_value, voucher_type) VALUES (UUID_TO_BIN(:voucherId), :discountValue, :voucherType)", toParamMap(voucher));
        if (update != 1)
            throw new RuntimeException("Nothing was inserted");
        return voucher;
    }

    @Override
    public Voucher assignToCustomer(Customer customer, Voucher voucher) {
        var update = jdbcTemplate.update("UPDATE vouchers SET owner_id = UUID_TO_BIN(:customerId) WHERE voucher_id = UUID_TO_BIN(:voucherId)", toParamMap(customer, voucher));
        if (update != 1)
            throw new RuntimeException("Failed to assign voucher to customer");
        return voucher;
    }

    @Override
    public int countAll() {
        return jdbcTemplate.queryForObject("select count(*) from vouchers", Collections.emptyMap(), Integer.class);
    }

    @Override
    public int countFixed() {
        return jdbcTemplate.queryForObject("select count(*) from vouchers where voucher_type = 'FIXED'", Collections.emptyMap(), Integer.class);
    }

    @Override
    public int countPercent() {
        return jdbcTemplate.queryForObject("select count(*) from vouchers where voucher_type = 'PERCENT'", Collections.emptyMap(), Integer.class);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)", Collections.singletonMap("voucherId", voucherId.toString().getBytes()), voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByOwnerId(UUID ownerId) {
        return jdbcTemplate.query("select * from vouchers where owner_id = UUID_TO_BIN(:ownerId)", Collections.singletonMap("ownerId", ownerId.toString().getBytes()), voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update("DELETE FROM vouchers where voucher_id = UUID_TO_BIN(:voucherId)", Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }


    @Override
    public void deleteByOwnerId(UUID ownerId) {
        jdbcTemplate.update("DELETE FROM vouchers where owner_id = :ownerId", Collections.singletonMap("ownerId", ownerId.toString().getBytes()));
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

}
