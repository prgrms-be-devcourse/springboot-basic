package org.prgms.management.wallet.repository;

import org.prgms.management.wallet.entity.Wallet;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public interface WalletRepository {
    Optional<Wallet> insert(Wallet wallet);

    List<Wallet> findAll();

    Optional<Wallet> findById(UUID walletId);

    Optional<Wallet> findByCustomerId(UUID customerId);

    Optional<Wallet> delete(Wallet wallet);

    void deleteAll();
}
