package com.weeklyMission.wallet.dto;

import com.weeklyMission.wallet.domain.Wallet;
import java.util.UUID;

public record WalletRequest(
    String memberId,
    String voucherId
) {

    public Wallet toEntity(){
        return new Wallet(UUID.randomUUID().toString(), memberId, voucherId);
    }
}
