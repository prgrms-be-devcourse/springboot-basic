package org.programmer.kdtspringboot.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class VoucherJdbcTemplateRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcTemplateRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public VoucherJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int update = jdbcTemplate.update(
                "insert into vouchers(voucher_id, value, type, created_at) values (UNHEX(REPLACE(?, '-', '')), ?, ?, ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getValue(),
                voucher.getType().getType(),
                Timestamp.valueOf(voucher.getCreatedAt())
        );
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update(
                "update vouchers set value = ?, type = ?, created_at = ? where voucher_id =UNHEX(REPLACE(?, '-', ''))",
                voucher.getValue(),
                voucher.getType().getType(),
                LocalDateTime.now(),
                voucher.getVoucherId().toString().getBytes()
        );
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(
                "select * from vouchers",
                voucherRowMapper
        );
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from vouchers where voucher_id = UNHEX(REPLACE(?, '-', ''))",
                    voucherRowMapper,
                    voucherId.toString().getBytes()
            ));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from vouchers");
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update("delete from vouchers where voucher_id = UNHEX(REPLACE(?, '-', ''))",
                voucherId.toString().getBytes()
        );
    }

    @Override
    public List<Voucher> findByType(String type) {

        return jdbcTemplate.query(
                "select * from vouchers where type = ?",
                voucherRowMapper,
                type
        );
    }

    @Override
    public List<Voucher> findByDate(LocalDate createdAt) {
        return jdbcTemplate.query("select * from vouchers where DATE(created_at) = ?",
                voucherRowMapper,
                createdAt);
    }

    private RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var amount = resultSet.getLong("value");
        var voucherType = VoucherType.getVoucherType(resultSet.getString("type"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        Voucher voucher;
        if (voucherType.getType().equals("FIX")) {
            voucher = new FixedAmountVoucher(voucherId, amount, createdAt);
        } else {
            voucher = new PercentDiscountVoucher(voucherId, amount, createdAt);
        }
        return voucher;
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

}
