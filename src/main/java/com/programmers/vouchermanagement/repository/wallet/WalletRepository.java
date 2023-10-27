package com.programmers.vouchermanagement.repository.wallet;

import com.programmers.vouchermanagement.domain.wallet.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    List<Wallet> findAll();

    Optional<Wallet> findById(UUID id);

    List<Wallet> findByCustomerId(UUID customerId);

    List<Wallet> findByVoucherId(UUID voucherId);

    Wallet save(Wallet wallet);

    int delete(UUID customerId, UUID voucherId);

    int deleteByVoucherId(UUID voucherId);

    int deleteByCustomerId(UUID customerId);
}
