package com.prgrms.vouchermanagement.wallet;

import java.util.List;
import java.util.Optional;

public interface VoucherWalletRepository {

    Long save(Wallet wallet);

    void removeWallet(Long walletId);

    Optional<Wallet> findWallet(Long walletId);

    List<Wallet> findAll();
}
