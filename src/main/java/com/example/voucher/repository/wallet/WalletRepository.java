package com.example.voucher.repository.wallet;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.example.voucher.domain.wallet.Wallet;

@Repository
public interface WalletRepository {

    Wallet save(Wallet wallet);

    List<Wallet> findByConditionId (String condition, UUID conditionID);

}
