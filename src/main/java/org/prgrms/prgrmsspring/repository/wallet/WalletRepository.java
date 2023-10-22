package org.prgrms.prgrmsspring.repository.wallet;

import org.prgrms.prgrmsspring.entity.wallet.Wallet;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {
    Wallet allocateVoucherToCustomer(UUID customerId, UUID voucherId);

    List<UUID> findVoucherIdListByCustomerId(UUID customerId);
}
