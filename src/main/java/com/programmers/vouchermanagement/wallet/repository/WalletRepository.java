package com.programmers.vouchermanagement.wallet.repository;

import com.programmers.vouchermanagement.wallet.domain.Wallet;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {

    void save(Wallet wallet);

    List<Wallet> findByCustomerId(UUID customerId);

    List<Wallet> findByVoucherId(UUID voucherId);

    void deleteByCustomerId(UUID customerId);

    List<Wallet> findAll();
}
