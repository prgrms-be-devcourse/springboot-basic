package org.prgrms.kdt.wallet;

import java.util.List;
import java.util.Optional;

public interface WalletRepository {
    Wallet save(Wallet wallet);

    Optional<Wallet> findById(String walletId);

    List<Wallet> findByCustomerId(String customerId);

    List<Wallet> findByVoucherId(String voucherId);

    void deleteByCustomerId(String customerId);
}
