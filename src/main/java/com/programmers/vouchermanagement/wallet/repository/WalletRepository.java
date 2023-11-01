package com.programmers.vouchermanagement.wallet.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.wallet.domain.Ownership;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    void save(Ownership ownership);

    List<Voucher> findAllVoucherByCustomerId(UUID customerId);

    void delete(UUID voucherID);

    Optional<Customer> findCustomerByVoucherId(UUID voucherId);

    void deleteAll();
}
