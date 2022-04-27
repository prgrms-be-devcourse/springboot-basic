package org.programmer.kdtspringboot.wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    Wallet insert(Wallet wallet);
    Optional<Wallet> findByWalletId(UUID walletId);
    List<Wallet> findByCustomerId(UUID customerId);
    List<Wallet> findByVoucherId(UUID voucherId);
    List<Wallet> findAll();
    void deleteVoucher(UUID voucherId);
}
