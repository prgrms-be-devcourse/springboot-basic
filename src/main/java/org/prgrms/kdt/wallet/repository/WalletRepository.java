package org.prgrms.kdt.wallet.repository;

import java.util.UUID;
import org.prgrms.kdt.wallet.model.Wallet;

public interface WalletRepository {

    Wallet insert(Wallet wallet);

    void deleteByCustomerVoucher(UUID customerId, UUID voucherId);
}
