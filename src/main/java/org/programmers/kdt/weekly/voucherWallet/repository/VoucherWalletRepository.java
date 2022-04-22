package org.programmers.kdt.weekly.voucherWallet.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.programmers.kdt.weekly.voucherWallet.model.VoucherWallet;

public interface VoucherWalletRepository {

    VoucherWallet insert(VoucherWallet voucherWallet);

    List<VoucherWallet> findAll(UUID customerId);

    Optional<VoucherWallet> findById(UUID customerId, UUID walletId);

    Optional<UUID> deleteById(UUID customerId, UUID walletId);

    void deleteAll(UUID customerId);
}