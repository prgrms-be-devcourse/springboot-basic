package com.prgrms.vouchermanagement.wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherWalletRepository {

    void save(Wallet wallet);

    void removeWallet(UUID walletId);

    Optional<Wallet> findWallet(UUID walletId);

    List<Wallet> findAll();
}
