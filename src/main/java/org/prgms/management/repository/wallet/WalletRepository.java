package org.prgms.management.repository.wallet;

import org.prgms.management.model.wallet.Wallet;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface WalletRepository {
    Wallet insert(Wallet wallet);

    List<Wallet> findAll();

    List<Wallet> findById(UUID walletId);

    List<Wallet> findByCustomerId(UUID customerId);

    Wallet delete(Wallet wallet);

    void deleteAll();
}
