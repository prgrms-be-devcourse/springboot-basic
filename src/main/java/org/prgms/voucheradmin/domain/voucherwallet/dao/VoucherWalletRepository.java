package org.prgms.voucheradmin.domain.voucherwallet.dao;

import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucherwallet.entity.VoucherWallet;

public interface VoucherWalletRepository {
    VoucherWallet create(VoucherWallet voucherWallet);

    List<VoucherWallet> findAll();

    // List<Customer> findVoucherOwners();// voucherId

    void deleteAllocatedVoucher(UUID customerId, UUID voucherId);
}
