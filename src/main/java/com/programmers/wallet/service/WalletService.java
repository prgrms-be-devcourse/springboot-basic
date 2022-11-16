package com.programmers.wallet.service;

import com.programmers.customer.Customer;
import com.programmers.voucher.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface WalletService {
    Customer assignVoucher(Customer customer, Voucher voucher);

    List<Voucher> searchVouchersByCustomerId(UUID customerId);

    Customer searchCustomerByVoucherId(UUID voucherId);

    void removeCustomerVoucher(Customer customer, Voucher voucher);
}
