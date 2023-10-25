package com.pgms.part1.domain.wallet.repository;

import com.pgms.part1.domain.wallet.entity.Wallet;

import java.util.List;

public interface WalletRepository {

    public List<Wallet> findWalletByCustomerId(Long id);
    public List<Wallet> findWalletByVoucherId(Long id);
    public void addWallet(Wallet wallet);
    public void deleteWallet(Long id);

}
