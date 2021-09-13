package org.prgrms.kdt.wallet;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {

    List<Wallet> findByCustomerId(UUID customerId);

    List<Wallet> findByWalletId(UUID walletId);

    int insert(UUID customerId);

    List<Wallet> findAll();
}
