package org.promgrammers.springbootbasic.domain.wallet.repository;

import org.promgrammers.springbootbasic.domain.wallet.model.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    Wallet save(Wallet wallet);

    List<Wallet> findAll();

    Optional<Wallet> findById(UUID walletId);

    List<Wallet> findAllWalletByCustomerId(UUID customerId);

    List<Wallet> findAllWalletByVoucherId(UUID voucherId);

    void deleteAll();

    void deleteById(UUID id);
}
