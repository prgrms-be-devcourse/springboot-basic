package com.example.voucher.repository.wallet;

import org.springframework.stereotype.Repository;
import com.example.voucher.domain.wallet.Wallet;

@Repository
public interface WalletRepository {

    Wallet save(Wallet wallet);

}
