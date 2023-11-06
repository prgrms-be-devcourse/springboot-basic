package com.weeklyMission.wallet.dto;

import com.weeklyMission.wallet.domain.Wallet;

public record WalletResponse(
    String memberId,
    String voucherId
) {
    public static WalletResponse of(Wallet wallet){
        return new WalletResponse(wallet.memberId(), wallet.voucherId());
    }
}
