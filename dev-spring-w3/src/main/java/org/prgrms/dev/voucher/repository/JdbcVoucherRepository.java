package org.prgrms.dev.voucher.repository;

import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"dev"})
public class JdbcVoucherRepository implements VoucherRepository {

    private static final int SUCCESS = 1;
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String voucherType = resultSet.getString("voucher_type");
        long voucherDiscount = resultSet.getLong("discount");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return VoucherType.getVoucherType(voucherType, voucherId, voucherDiscount, createdAt);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        Map<String, Object> params = Map.of("voucherId", voucher.getVoucherId().toString().getBytes(),
                "voucherType", voucher.getVoucherType().name(),
                "discount", voucher.getDiscountValue(),
                "createdAt", Timestamp.valueOf(voucher.getCreatedAt()));

        int insert = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, voucher_type, discount, created_at) " +
                        "VALUES (UUID_TO_BIN(:voucherId), :voucherType, :discount, :createdAt)",
                params);
        if (insert != SUCCESS) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        Map<String, Object> params = Map.of("voucherId", voucher.getVoucherId().toString().getBytes()
                , "discount", voucher.getDiscountValue());

        int update = jdbcTemplate.update("UPDATE vouchers SET discount = :discount WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                params);
        if (update != SUCCESS) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }
}
