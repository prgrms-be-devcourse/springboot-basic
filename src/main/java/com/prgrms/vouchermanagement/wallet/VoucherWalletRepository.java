package com.prgrms.vouchermanagement.wallet;

import com.prgrms.vouchermanagement.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherWalletRepository {

    void save(Wallet wallet);

    void removeWallet(UUID walletId);

    List<Customer> findCustomerByVoucher(UUID voucherId);

    Optional<Wallet> findWallet(UUID walletId);

    List<Wallet> findAll();
}
