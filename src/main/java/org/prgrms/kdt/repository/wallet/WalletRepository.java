package org.prgrms.kdt.repository.wallet;

import java.util.UUID;
import org.prgrms.kdt.model.wallet.Wallet;

public interface WalletRepository {

    Wallet insert(Wallet wallet);

    void deleteByCustomerVoucher(UUID customerId, UUID voucherId);
}
