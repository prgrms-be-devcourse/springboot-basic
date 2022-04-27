package com.kdt.commandLineApp.service.voucherWallet;

import com.kdt.commandLineApp.service.customer.Customer;
import com.kdt.commandLineApp.service.voucher.Voucher;

import java.util.List;

public interface VoucherWalletRepository {
    public void giveVoucherToCustomer(String customerId, String voucherId);

    public void deleteVoucherFromCustomer(String customerId, String voucherId);

    public List<Voucher> getCustomerVouchers(String customerId);

    public List<Customer> getCustomersWithVoucherId(String voucherId);
}
