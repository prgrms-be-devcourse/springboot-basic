package com.programmers.vouchermanagement.wallet.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    void save(UUID voucherId, UUID customerId);

    List<Voucher> findAllVoucherByCustomerId(UUID customerId);

    void delete(UUID voucherId, UUID customerId);

    Optional<Customer> findCustomerByVoucherId(UUID voucherId);
}
