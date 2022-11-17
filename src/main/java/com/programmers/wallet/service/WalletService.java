package com.programmers.wallet.service;

import com.programmers.customer.Customer;
import com.programmers.voucher.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface WalletService {
    Customer assignVoucher(UUID customerId, UUID voucherId);

    List<Voucher> searchVouchersByCustomerId(UUID customerId);

    Customer searchCustomerByVoucherId(UUID voucherId);

    void removeCustomerVoucher(UUID customerId, UUID voucherId);
}
