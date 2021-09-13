package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.wallet.VoucherWallet;
import org.prgms.w3d1.util.Util;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class DatabaseVoucherWalletRepository implements VoucherWalletRepository {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseVoucherWalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<VoucherWallet> rowMapper = (rs, rowNum) -> {
        var voucherWalletId = Util.toUUID(rs.getBytes("voucher_wallet_id"));
        var customerId = Util.toUUID(rs.getBytes("customer_id"));
        return new VoucherWallet(voucherWalletId, customerId);
    };

    @Override
    public Optional<VoucherWallet> findById(UUID voucherWalletId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from my_order_mgmt.voucher_wallet where voucher_wallet_id = UUID_TO_BIN(?)",
            rowMapper, voucherWalletId.toString().getBytes()));
    }

    @Override
    public Optional<VoucherWallet> findByCustomerId(UUID customerId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from my_order_mgmt.voucher_wallet where customer_id = UUID_TO_BIN(?)",
            rowMapper, customerId.toString().getBytes()));
    }

    @Override
    public void insert(VoucherWallet voucherWallet) {
        var insertCount = jdbcTemplate.update(
            "insert into my_order_mgmt.voucher_wallet(voucher_wallet_id, customer_id) VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?))",
            voucherWallet.getVoucherWalletId().toString().getBytes(),
            voucherWallet.getCustomerId().toString().getBytes());

        if (insertCount != 1) {
            throw new RuntimeException("Nothing was Inserted" + DatabaseVoucherWalletRepository.class.getCanonicalName());
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from my_order_mgmt.voucher_wallet");
    }
}
