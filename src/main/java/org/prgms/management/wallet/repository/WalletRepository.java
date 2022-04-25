package org.prgms.management.wallet.repository;

import org.prgms.management.wallet.vo.Wallet;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface WalletRepository {
    Optional<Wallet> insert(Wallet wallet);

    List<Wallet> findAll();

    List<Wallet> findById(UUID walletId);

    List<Wallet> findByCustomerId(UUID customerId);

    Optional<Wallet> delete(Wallet wallet);

    void deleteAll();
}
