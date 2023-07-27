package com.example.voucher.wallet.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.example.voucher.wallet.model.Wallet;

@Repository
public interface WalletRepository {

    Wallet save(Wallet wallet);

    List<Wallet> findByConditionId(String condition, UUID conditionID);

    Wallet findById(UUID walletID);

    void deleteById(UUID customerId, UUID voucherId);

}
