package com.tangerine.voucher_system.application.wallet.repository;

import com.tangerine.voucher_system.application.wallet.model.Wallet;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {

    void insert(Wallet wallet);

    void update(Wallet wallet);

    void deleteById(UUID walletId);

    List<Wallet> findByCustomerId(UUID customerId);

    List<Wallet> findByVoucherId(UUID voucherId);

}
