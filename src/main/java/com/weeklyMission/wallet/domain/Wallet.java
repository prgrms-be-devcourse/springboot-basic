package com.weeklyMission.wallet.domain;

import java.util.UUID;

public record Wallet(
    UUID walletId,
    UUID memberId,
    UUID voucherId
) {

}
