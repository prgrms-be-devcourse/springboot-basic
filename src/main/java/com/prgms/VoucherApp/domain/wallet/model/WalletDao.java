package com.prgms.VoucherApp.domain.wallet.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletDao {

    void save(Wallet wallet);

    Optional<Wallet> findById(UUID walletId);

    List<Wallet> findByCustomerId(UUID customerId);

    Optional<Wallet> findByVoucherId(UUID voucherId);

    void deleteById(UUID walletId);
}
