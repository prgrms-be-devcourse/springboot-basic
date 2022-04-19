package com.kdt.commandLineApp;

import com.kdt.commandLineApp.customer.Customer;
import com.kdt.commandLineApp.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherWalletRepository {
    public void giveVoucherToCustomer(UUID customerId, UUID voucherId);

    public void deleteVoucherFromCustomer(UUID customerId, UUID voucherId);

    public List<Voucher> getCustomerVouchers(UUID customerId);

    public List<Customer> getCustomersWithVoucherId(UUID voucherId);
}
