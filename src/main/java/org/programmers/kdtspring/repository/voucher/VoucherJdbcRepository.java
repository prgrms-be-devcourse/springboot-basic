package org.programmers.kdtspring.repository.voucher;

import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.voucher.FixedAmountVoucher;
import org.programmers.kdtspring.entity.voucher.PercentDiscountVoucher;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.entity.voucher.VoucherType;
import org.programmers.kdtspring.exception.VoucherDeleteFailed;
import org.programmers.kdtspring.exception.VoucherInsertFailed;
import org.programmers.kdtspring.exception.VoucherUpdateFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class VoucherJdbcRepository implements VoucherRepository {

    private static final Logger log = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Voucher> mapToVoucher = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var type = resultSet.getString("type");
        var customerId = resultSet.getBytes("customer_id") != null ?
                toUUID(resultSet.getBytes("customer_id")) : null;
        if (type.equals(VoucherType.FixedAmountVoucher.toString())) {
            var amount = resultSet.getInt("amount");
            return new FixedAmountVoucher(voucherId, customerId, amount, type);
        } else {
            var percent = resultSet.getInt("percent");
            return new PercentDiscountVoucher(voucherId, customerId, percent, type);
        }
    };

    public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public void insert(Voucher voucher) {
        log.info("[voucherJdbcRepository] save() called");

        if (voucher instanceof FixedAmountVoucher) {
            int insert = jdbcTemplate.update(
                    "INSERT INTO VOUCHERS(voucher_id, type, amount, percent, customer_id) VALUES (UUID_TO_BIN(?), ?, ?, ?, ?)",
                    voucher.getVoucherId().toString().getBytes(),
                    VoucherType.FixedAmountVoucher.toString(),
                    voucher.getDiscount(),
                    null,
                    null);
            if (insert != 1) {
                throw new VoucherInsertFailed("Nothing was saved");
            }
            return;
        }

        if (voucher instanceof PercentDiscountVoucher) {
            int insert = jdbcTemplate.update(
                    "INSERT INTO vouchers(voucher_id, type, amount, percent, customer_id) VALUES (UUID_TO_BIN(?), ?, ?, ?, ?)",
                    voucher.getVoucherId().toString().getBytes(),
                    VoucherType.PercentDiscountVoucher.toString(),
                    null,
                    voucher.getDiscount(),
                    null);
            if (insert != 1) {
                throw new VoucherInsertFailed("Nothing was saved");
            }
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        log.info("[voucherJdbcRepository] findById() called");

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)",
                    mapToVoucher,
                    voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCustomer(Customer customer) {
        return jdbcTemplate.query(
                "SELECT * FROM vouchers WHERE customer_id = UUID_TO_BIN(?)",
                mapToVoucher,
                customer.getCustomerId().toString().getBytes());
    }

    @Override
    public List<Voucher> findByType(String voucherType) {
        return jdbcTemplate.query("SELECT * FROM vouchers WHERE type = ?;", mapToVoucher, voucherType);
    }

    @Override
    public List<Voucher> findAll() {
        log.info("[voucherJdbcRepository] findAll() called");
        return jdbcTemplate.query("SELECT * FROM vouchers", mapToVoucher);
    }

    @Override
    public void updateCustomerId(Voucher voucher) {
        log.info("[VoucherService] updateCustomerId called()");

        int update = jdbcTemplate.update(
                "UPDATE vouchers SET customer_id = UUID_TO_BIN(?) WHERE voucher_id = uuid_to_bin(?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getVoucherId().toString().getBytes());
        if (update != 1) {
            throw new VoucherUpdateFailed("Nothing was updated");
        }
    }

    @Override
    public void deleteOne(Voucher voucher) {
        log.info("[voucherJdbcRepository] deleteOne() called");

        int delete = jdbcTemplate.update(
                "DELETE  FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)",
                voucher.getVoucherId().toString().getBytes());
        if (delete != 1) {
            throw new VoucherDeleteFailed("Nothing was deleted");
        }
    }

    @Override
    public void deleteAll() {
        log.info("[voucherJdbcRepository] deleteAll() called");

        jdbcTemplate.update("DELETE FROM vouchers");
    }
}