package com.programmers.wallet.service;

import com.programmers.customer.Customer;

import java.util.UUID;

public interface WalletService {
    Customer assignVoucher(UUID customerId, UUID voucherId);

    void removeCustomerVoucher(UUID customerId, UUID voucherId);
}
