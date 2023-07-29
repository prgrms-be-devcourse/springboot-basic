package org.prgrms.kdt.wallet.dao;

import org.prgrms.kdt.wallet.domain.Wallet;

import java.util.UUID;

public interface WalletCommandRepository {
    Wallet insert(Wallet wallet);

    void deleteById(UUID walletId);
}
