package org.programs.kdt.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    List<Wallet> findAll();
    Wallet insert(Wallet wallet);
    List<Wallet> findByVoucherId(UUID voucherId);
    List<Wallet> findByCustomerId(UUID customerId);
    Optional<Wallet> findById(UUID voucherWalletId);
    List<Wallet> findByCustomerEmail(String email);

    void deleteByCustomerId(UUID customerId);
    void deleteByVoucherId(UUID voucherId);
    void deleteById(UUID walletId);
    void deleteAll();
    boolean existWalletId(UUID walletId);
}
