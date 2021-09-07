package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.voucher.VoucherFactory;
import org.prgms.w3d1.model.voucher.VoucherType;
import org.prgms.w3d1.util.Util;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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

    private final RowMapper<Voucher> rowMapper = (rs, rowNum) -> {
        var voucherId = Util.toUUID(rs.getBytes("voucher_id"));
        var value = rs.getLong("value");
        var voucherType = rs.getString("voucher_type");
        var voucherWalletId = rs.getBytes("wallet_id") != null ?
            Util.toUUID(rs.getBytes("wallet_id")) : null;
        return VoucherFactory.getVoucher(voucherId, value, voucherType, voucherWalletId);
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
    public List<Voucher> findByVoucherWalletId(UUID voucherWalletId) {
        return jdbcTemplate.query("select * from my_order_mgmt.vouchers where wallet_id = UUID_TO_BIN(?)", rowMapper,
            voucherWalletId.toString().getBytes());
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return jdbcTemplate.query("select * from my_order_mgmt.vouchers where voucher_type = ?",
            rowMapper, voucherType.getType());
    }

    @Override
    public void save(Voucher voucher) {
        var saveCount = jdbcTemplate.update("insert into vouchers(voucher_id, value, voucher_type, wallet_id) values (UUID_TO_BIN(?), ?, ?, UUID_TO_BIN(?))",
            voucher.getVoucherId().toString().getBytes(),
            voucher.getVoucherValue(),
            voucher.getClass().getSimpleName(),
            voucher.getVoucherWalletId() != null ? voucher.getVoucherWalletId().toString().getBytes() : null);

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
    public void deleteById(UUID voucherId) {
        var deleteCount = jdbcTemplate.update("delete from vouchers where voucher_id=UUID_TO_BIN(?)",
            voucherId.toString().getBytes());

        if (deleteCount != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }
}
