package com.weeklyMission.wallet.repository;

import com.weeklyMission.wallet.domain.Wallet;
import java.util.List;
import java.util.UUID;

public interface WalletRepository {

    void save(Wallet wallet);

    List<Wallet> findByMemberId(String id);

    List<Wallet> findByVoucherId(String id);

    void deleteById(String memberId, String voucherId);

}
