package org.prgrms.kdtspringdemo.wallet.repository;

import org.prgrms.kdtspringdemo.wallet.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    Wallet insert(Wallet customer);
    Wallet update(Wallet customer);
    int count();
    List<Wallet> findAll();
    Optional<Wallet> findById(UUID walletId);
    Optional<Wallet> findByCustomerId(UUID customerId);

    void deleteAll();

    void deleteByCustomerId(String customerId);

    void deleteByVoucherId(String voucherId);
}
