package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.Factory.VoucherFactory;
import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Transactional(readOnly = true)
@Repository
@Profile({"staging"})
public class JdbcVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public static RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherType type = VoucherType.valueOf(resultSet.getString("type"));
        long value = resultSet.getLong("value");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return VoucherFactory.createVoucher(voucherId, type, value, createdAt);
    };

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public Voucher insert(Voucher voucher) {

        Assert.notNull(voucher, "Voucher should not be null");

        String insertSql = "INSERT INTO vouchers(voucher_id, value, type, created_at) VALUES(UUID_TO_BIN(?), ?, ?, ?)";

        int insert = jdbcTemplate.update(insertSql,
                voucher.getVoucherId().toString().getBytes(),
                voucher.getValue(),
                voucher.getType().toString(),
                Timestamp.valueOf(voucher.getCreatedAt()));

        if (insert != 1)
            throw new RuntimeException("Nothing to inserted");

        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {

        int update = jdbcTemplate.update("UPDATE vouchers SET value = ? WHERE voucher_id = UUID_TO_BIN(?)",
                voucher.getValue(),
                voucher.getVoucherId().toString().getBytes());

        if (update != 1)
            throw new RuntimeException("Nothing was updated");

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        Assert.notNull(voucherId, "Voucher id should not be null");

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)", voucherRowMapper, voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Transactional
    @Override
    public void delete(Voucher voucher) {

        Assert.notNull(voucher, "Voucher should not be null");

        deleteById(voucher.getVoucherId());

    }

    @Transactional
    @Override
    public void deleteById(UUID voucherId) {

        Assert.notNull(voucherId, "Voucher id should not be null");

        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)", voucherId.toString().getBytes());
    }

    @Transactional
    @Override
    public void deleteAll() {
        for (Voucher voucher : findAll()) {
            deleteById(voucher.getVoucherId());
        }
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
