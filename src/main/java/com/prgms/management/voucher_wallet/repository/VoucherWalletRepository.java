package com.prgms.management.voucher_wallet.repository;

import com.prgms.management.customer.model.Customer;
import com.prgms.management.voucher_wallet.entity.VoucherWallet;

import java.util.List;
import java.util.UUID;

public interface VoucherWalletRepository {
    VoucherWallet giveVoucherToCustomer(VoucherWallet voucherWallet);

    List<VoucherWallet> findByCustomer(Customer customer);

    Customer findCustomerByVoucherId(UUID voucherId);
}
