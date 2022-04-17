package org.prgms.voucheradmin.domain.voucherwallet.dao;

import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucherwallet.entity.VoucherWallet;

public interface VoucherWalletRepository {
    VoucherWallet create(VoucherWallet voucherWallet);

    List<VoucherWallet> findAll();

    // List<Voucher> findAllocatedVouchers();

    // List<Customer> findVoucherOwners();// voucherId

    void deleteAllocatedVoucher(UUID customerId, UUID voucherId);
}
