package com.kdt.commandLineApp.voucherWallet;

import com.kdt.commandLineApp.customer.Customer;
import com.kdt.commandLineApp.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherWalletRepository {
    public void giveVoucherToCustomer(String customerId, String voucherId);

    public void deleteVoucherFromCustomer(String customerId, String voucherId);

    public List<Voucher> getCustomerVouchers(String customerId);

    public List<Customer> getCustomersWithVoucherId(String voucherId);
}
