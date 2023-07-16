package com.programmers.springweekly.repository.wallet;

import com.programmers.springweekly.domain.wallet.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    Wallet save(Wallet wallet);

    Optional<Wallet> findByCustomerId(UUID customerId);

    List<Wallet> findByVoucherId(UUID voucherId);

    int deleteByWalletId(UUID walletId);

    List<Wallet> findAll();

    boolean existByWalletId(UUID walletId);
}
