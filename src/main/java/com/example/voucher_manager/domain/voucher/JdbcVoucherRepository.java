package com.example.voucher_manager.domain.voucher;

import com.example.voucher_manager.domain.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class JdbcVoucherRepository implements VoucherRepository{
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var discountInformation = resultSet.getLong("discount_information");
        var voucherType = resultSet.getString("voucher_type");
        var ownerId = resultSet.getBytes("owner_id") != null ?
                toUUID(resultSet.getBytes("owner_id")) : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        if (VoucherType.of(voucherType).equals(VoucherType.FIXED)){
            return FixedAmountVoucher.of(voucherId, discountInformation, VoucherType.of(voucherType), ownerId, createdAt);
        }
        return PercentDiscountVoucher.of(voucherId, discountInformation, VoucherType.of(voucherType), ownerId, createdAt);
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{ // 순서 상관없이 key로 mapping이됨
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("discountInformation", voucher.getDiscountInformation());
            put("voucherType", voucher.getVoucherType().getType());
            put("ownerId", voucher.getOwnerId() != null ?
                    voucher.getOwnerId().toString().getBytes() : null);
            put("createdAt", voucher.getCreatedAt() != null ?
                    voucher.getCreatedAt() : null);
        }};
    }

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper); // query와 RowMapper 전달
    }

    @Override
    public List<Voucher> findVoucherListByCustomer(Customer customer) {
        return jdbcTemplate.query("select * from vouchers where owner_id = UUID_TO_BIN(:ownerId)",
                Collections.singletonMap("ownerId", customer.getCustomerId().toString().getBytes()),
                voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got Empty Result", e);
            return Optional.empty();
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        var update = jdbcTemplate.update("UPDATE vouchers SET discount_information = :discountInformation, owner_id = UUID_TO_BIN(:ownerId) WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucher));
        if (update != 1) {
            logger.error("Noting was updated");
        }
        return voucher;
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        try {
            var insert = jdbcTemplate.update(
                    "INSERT INTO vouchers(voucher_id, discount_information, voucher_type, owner_id) VALUES (UUID_TO_BIN(:voucherId), :discountInformation, :voucherType, UUID_TO_BIN(:ownerId))",
                    toParamMap(voucher)
            );
            if (insert != 1) {
                logger.error("Noting was inserted");
                return Optional.empty();
            }
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException("Duplicate entry can't inserted");
        }
        return Optional.of(voucher);
    }

    @Override
    public void clear() {
        jdbcTemplate.update("DELETE FROM vouchers", Map.of());
    }

    @Override
    public void deleteVoucherByCustomer(Voucher voucher, Customer customer) {
        var delete = jdbcTemplate.update("DELETE FROM vouchers where voucher_id = UUID_TO_BIN(:voucherId) and owner_id = UUID_TO_BIN(:ownerId)",
                Map.of(
                        "voucherId", voucher.getVoucherId().toString().getBytes(),
                        "ownerId", customer.getCustomerId().toString().getBytes()
                ));
        if (delete != 1){
            logger.error("Noting was deleted");
        }
    }

    @Override
    public boolean deleteVoucherById(UUID voucherId) {
        var delete = jdbcTemplate.update("DELETE FROM vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                Map.of("voucherId", voucherId.toString().getBytes()));
        if (delete != 1){
            logger.error("Noting was deleted");
            return false;
        }
        return true;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
