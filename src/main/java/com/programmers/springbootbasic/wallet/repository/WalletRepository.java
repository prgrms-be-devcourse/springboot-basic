package com.programmers.springbootbasic.wallet.repository;

import com.programmers.springbootbasic.customer.domain.Customer;
import com.programmers.springbootbasic.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    void updateVoucherCustomerId(UUID customerId, UUID voucherId);

    List<Voucher> findVouchersByCustomerId(UUID customerId);

    Optional<Customer> findCustomerByVoucherId(UUID voucherId);

    void deleteVoucherByVoucherIdAndCustomerId(UUID voucherId, UUID customerId);

    void deleteAllVouchersByCustomerId(UUID customerId);
}
