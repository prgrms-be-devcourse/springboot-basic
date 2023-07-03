package me.kimihiqq.vouchermanagement.domain.voucherwallet.repository;

import java.util.Set;
import java.util.UUID;

public interface VoucherWalletRepository {
    void addVoucherToWallet(UUID customerId, UUID voucherId);
    void removeVoucherFromWallet(UUID customerId, UUID voucherId);
    Set<UUID> findVoucherIdsByCustomerId(UUID customerId);
    void deleteByCustomerId(UUID customerId);
    Set<UUID> findCustomerIdsByVoucherId(UUID voucherId);

}
