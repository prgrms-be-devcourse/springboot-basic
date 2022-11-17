package com.programmers.wallet.repository;

import com.programmers.customer.Customer;
import com.programmers.voucher.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    Customer assignVoucher(Customer customer, Voucher voucher);

    List<Voucher> findVouchersByCustomerId(UUID customerId);

    Optional<Customer> findCustomerByVoucherId(UUID voucherId);

    void deleteCustomerVoucher(UUID customerId, UUID voucherId);

    void deleteAll();
}
