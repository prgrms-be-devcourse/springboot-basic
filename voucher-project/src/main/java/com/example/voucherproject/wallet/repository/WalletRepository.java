package com.example.voucherproject.wallet.repository;

import com.example.voucherproject.wallet.domain.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    // C R U D
    // insert   (C)
    Wallet insert(Wallet wallet);

    // find     (R)
    Optional<Wallet> findByIds(UUID userId, UUID voucherId);
    List<Wallet> findAll();
    // update   (U)

    // delete   (D)
    int deleteById(UUID id);
    int deleteAll();

    int count();

    List<Wallet> findByUserId(UUID userId);

    List<Wallet> findByVoucherId(UUID voucherId);
}
