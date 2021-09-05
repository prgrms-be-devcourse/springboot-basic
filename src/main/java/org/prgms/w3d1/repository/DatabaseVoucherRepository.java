package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.voucher.VoucherFactory;
import org.prgms.w3d1.model.wallet.VoucherWallet;
import org.prgms.w3d1.util.Util;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("dev")
public class DatabaseVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Voucher> rowMapper = (rs, rowNum) -> {
        var voucherId = Util.toUUID(rs.getBytes("voucher_id"));
        var value = rs.getLong("value");
        var voucherType = rs.getString("voucher_type");
        return VoucherFactory.getVoucher(voucherId, value, voucherType);
    };

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "select * from vouchers where voucher_id = UUID_TO_BIN(?)",
                    rowMapper, voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Voucher> findByCustomerId(UUID customerId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "select * from vouchers where customer_id = UUID_TO_BIN(?)",
                    rowMapper, customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public VoucherWallet findVoucherWallet(UUID customerId) {
        return new VoucherWallet(jdbcTemplate.query("select * from vouchers where customer_id = UUID_TO_BIN(?)",
            rowMapper,
            customerId.toString().getBytes()));
    }

    @Override
    public void save(Voucher voucher) {
        var saveCount = jdbcTemplate.update("insert into vouchers(voucher_id, value, voucher_type) values (UUID_TO_BIN(?), ?, ?)",
            voucher.getVoucherId().toString().getBytes(),
            voucher.getVoucherValue(),
            voucher.getClass().getSimpleName());

        if (saveCount != 1) {
            throw new RuntimeException("Nothing was saved");
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", rowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from vouchers");
    }

    @Override
    public void deleteCustomerVoucher(UUID customerId, UUID voucherId) {
        var deleteCount = jdbcTemplate.update(
            "delete from vouchers where customer_id=UUID_TO_BIN(?) and voucher_id=UUID_TO_BIN(?)",
            customerId.toString().getBytes(),
            voucherId.toString().getBytes()
        );

        if (deleteCount != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }

    public void assignCustomer(UUID customerId, UUID voucherId) {
        var updateCount = jdbcTemplate.update("update vouchers set customer_id=UUID_TO_BIN(?) where voucher_id=UUID_TO_BIN(?)",
            customerId.toString().getBytes(),
            voucherId.toString().getBytes());

        if (updateCount != 1) {
            throw new RuntimeException("Nothing was updated");
        }
    }
}
