package org.prgms.management.wallet.repository;

import org.prgms.management.wallet.entity.Wallet;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public interface WalletRepository {
    Optional<Wallet> insert(Wallet wallet);

    Map<UUID, Wallet> getAll();

    Optional<Wallet> getById(UUID walletId);

    Optional<Wallet> getByCustomerId(UUID customerId);

    Optional<Wallet> delete(UUID walletId);

    void deleteAll();
}
