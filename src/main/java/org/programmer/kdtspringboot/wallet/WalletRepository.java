package org.programmer.kdtspringboot.wallet;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {

    Wallet insert(Wallet wallet);
    List<Wallet> findByCustomerId(UUID customerId);
    List<Wallet> findByVoucherId(UUID voucherId);
    List<Wallet> findAll();
    void deleteVoucher(Wallet wallet);
}
