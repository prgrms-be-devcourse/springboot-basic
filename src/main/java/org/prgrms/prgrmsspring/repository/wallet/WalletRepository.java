package org.prgrms.prgrmsspring.repository.wallet;

import org.prgrms.prgrmsspring.entity.wallet.Wallet;

import java.util.UUID;

public interface WalletRepository {
    Wallet allocateVoucherToCustomer(UUID customerId, UUID voucherId);
}
