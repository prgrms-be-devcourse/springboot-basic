package org.prgrms.kdt.wallet.dao;

import org.prgrms.kdt.wallet.domain.Wallet;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {
    Wallet insert(Wallet wallet);
    List<Wallet> findByMemberId(UUID memberId);
    List<Wallet> findByVoucherId(UUID voucherId);
    void deleteById(UUID walletId);
    List<Wallet> findAll();
}
