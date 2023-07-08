package com.programmers.springweekly.repository.wallet;

import com.programmers.springweekly.domain.wallet.Wallet;

import java.util.List;

public interface WalletRepository {

    Wallet save(Wallet wallet);

    Wallet findByCustomerId(String customerId);

    List<Wallet> findByVoucherId(String voucherId);

    void deleteByWalletId(String walletId);

    List<Wallet> findAll();

}
