package org.prgms.order.wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {

    Wallet insert(Wallet wallet);

    Optional<Wallet> findById(UUID walletId);
    List<Wallet> findByCustomerId(UUID customerId);
    List<Wallet> findByVoucherId(UUID voucherId);
    List<Wallet> findAll();


    List<Wallet> findByCustomerAvailable(UUID customerId);
    void useVoucher(Wallet wallet);

    void  deleteByWalletId(UUID walletId);
    void  deleteByCustomerId(UUID customerId);
    void deleteByVoucherId(UUID voucherId);

    void deleteAll();
}
