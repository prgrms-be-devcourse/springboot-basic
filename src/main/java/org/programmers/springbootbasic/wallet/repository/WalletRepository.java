package org.programmers.springbootbasic.wallet.repository;

import org.programmers.springbootbasic.customer.model.Customer;
import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.wallet.domain.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    List<Voucher> findVoucherByCustomerId(UUID customerId);

    List<Customer> findCustomerByVoucherId(UUID voucherId);

    Optional<Wallet> findById(UUID walletId);

    Wallet insert(Wallet wallet);

    void deleteById(UUID walletId);
}
