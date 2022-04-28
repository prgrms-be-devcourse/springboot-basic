package com.example.voucherproject.wallet.repository;

import com.example.voucherproject.wallet.model.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    Wallet insert(Wallet wallet);
    List<Wallet> findAll();
    Optional<Wallet> findByIds(UUID userId, UUID voucherId);
    List<Wallet> findAllByUserId(UUID userId);
    List<Wallet> findAllByVoucherId(UUID voucherId);
    Optional<Wallet> findById(UUID id);
    int count();
    int deleteById(UUID id);
    int deleteAll();
}
