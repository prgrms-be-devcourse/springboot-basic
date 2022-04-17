package org.prgms.voucheradmin.domain.voucherwallet.dao;

import static org.prgms.voucheradmin.global.util.Util.toUUID;

import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucherwallet.entity.VoucherWallet;
import org.prgms.voucheradmin.global.exception.CreationFailException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;



@Repository
public class JdbcVoucherWalletRepository implements VoucherWalletRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherWalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VoucherWallet create(VoucherWallet voucherWallet) {
        int update = jdbcTemplate.update("insert into voucher_wallets(voucher_wallet_id, customer_id, voucher_id) value(UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?))",
                voucherWallet.getVoucherWalletId().toString().getBytes(),
                voucherWallet.getCustomerId().toString().getBytes(),
                voucherWallet.getVoucherId().toString().getBytes());

        if(update != 1) {
            throw new CreationFailException();
        }

        return voucherWallet;
    }

    @Override
    public List<VoucherWallet> findAll() {
        return jdbcTemplate.query("select * from voucher_wallets", voucherWalletRowMapper);
    }

    @Override
    public void deleteAllocatedVoucher(UUID customerId, UUID voucherId) {

    }

    private final RowMapper<VoucherWallet> voucherWalletRowMapper = (resultSet, rowNum) -> {
        UUID voucherWalletId = toUUID(resultSet.getBytes("voucher_wallet_id"));
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));

        return new VoucherWallet(voucherWalletId, customerId, voucherId);
    };
}
