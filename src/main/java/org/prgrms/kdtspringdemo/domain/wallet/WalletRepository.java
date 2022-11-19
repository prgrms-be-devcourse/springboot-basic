package org.prgrms.kdtspringdemo.domain.wallet;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {
    List<UUID> findVoucherIdsByCustomerId(UUID customerId);

    List<UUID> findCustomerIdsByVoucherId(UUID voucherId);

    Wallet addWallet(Wallet wallet);

    void deleteWallet(Wallet wallet);

    void deleteAllWallet();

    int count();
}
