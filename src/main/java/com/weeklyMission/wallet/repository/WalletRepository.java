package com.weeklyMission.wallet.repository;

import com.weeklyMission.wallet.domain.Wallet;
import java.util.List;
import java.util.UUID;

public interface WalletRepository {

    void save(Wallet wallet);

    List<Wallet> findByMemberId(UUID id);

    List<Wallet> findByVoucherId(UUID id);

    void deleteById(UUID memberId, UUID voucherId);

}
