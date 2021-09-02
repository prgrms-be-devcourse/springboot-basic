package org.prgrms.kdt.voucher;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.exception.IllegalRowUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 6:48 오후
 */

@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var name = resultSet.getString("name");
        var discount = resultSet.getLong("discount");
        var voucherType = resultSet.getString("voucher_type");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Voucher(voucherId, name, discount, VoucherType.valueOf(voucherType), createdAt);
    };

    @Override
    public void insert(Voucher voucher) {
        int update = jdbcTemplate.update(
                "INSERT INTO vouchers(voucher_id, name, voucher_type, discount, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?, ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getName(),
                voucher.getVoucherType().name(),
                voucher.getDiscount(),
                Timestamp.valueOf(voucher.getCreatedAt()));

        if (update < 1) {
            throw new IllegalRowUpdateException("Nothing was inserted");
        }

    }

    @Override
    public void update(Voucher voucher) {
        int update = jdbcTemplate.update(
                "UPDATE vouchers SET name = ? WHERE voucher_id = UUID_TO_BIN(?)",
                voucher.getName(),
                voucher.getVoucherId().toString().getBytes());

        if (update < 1) {
            throw new IllegalRowUpdateException("Nothing was updated");
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate
                    .queryForObject(
                            "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)",
                            voucherRowMapper,
                            voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result ", e);
            return Optional.empty();
        }

    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return jdbcTemplate
                .query(
                        "SELECT * FROM vouchers WHERE voucher_type = ?",
                        voucherRowMapper,
                        voucherType.name());
    }

    @Override
    public void deleteById(UUID voucherId) {
        int update = jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)",
                voucherId.toString().getBytes());

        if (update != 1) {
            throw new RuntimeException("Nothing was delete");
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers");
    }

    public int count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM vouchers", Integer.class);
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
