package com.weeklyMission.wallet.domain;

import java.util.UUID;

public record Wallet(
    String walletId,
    String memberId,
    String voucherId
) {

}
