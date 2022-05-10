package com.kdt.commandLineApp.service.voucherWallet;

import com.kdt.commandLineApp.service.customer.Customer;
import com.kdt.commandLineApp.service.voucher.Voucher;

import java.util.List;

public interface VoucherWalletRepository {
    public void giveVoucherToCustomer(long customerId, long voucherId);

    public void deleteVoucherFromCustomer(long customerId, long voucherId);

    public List<Voucher> getCustomerVouchers(long customerId);

    public List<Customer> getCustomersWithVoucherId(long voucherId);
}
