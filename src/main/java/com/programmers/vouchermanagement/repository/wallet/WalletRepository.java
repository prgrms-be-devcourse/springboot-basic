package com.programmers.vouchermanagement.repository.wallet;

import com.programmers.vouchermanagement.domain.wallet.Wallet;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {
    List<Wallet> findAll();

    List<Wallet> findByCustomerId(UUID customerId);

    List<Wallet> findByVoucherId(UUID voucherId);

    Wallet save(Wallet wallet);

    int delete(UUID uuid);
}
