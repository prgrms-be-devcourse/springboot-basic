package com.example.voucher.wallet.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.example.voucher.wallet.model.Wallet;

@Repository
public interface WalletRepository {

    Wallet save(Wallet wallet);

    Wallet findById(UUID walletID);

    List<Wallet> findByCustomerId(UUID customerId);

    List<Wallet> findByVoucherId(UUID voucherId);

    void deleteById(UUID customerId, UUID voucherId);

}
