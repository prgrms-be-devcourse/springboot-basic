package org.prgrms.kdtspringdemo.wallet.repository;

import org.prgrms.kdtspringdemo.wallet.domain.Wallet;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface WalletRepository {
    Wallet insert(Wallet wallet);
    Optional<Wallet> findById(UUID voucherId);
    Optional<List<UUID>> findVouchersByCustomerId(UUID customerId);
    List<UUID> deleteVoucherByVoucherId(UUID customerId, UUID voucherId);
    void findCustomerByVoucherId(UUID voucherId);
    
}
