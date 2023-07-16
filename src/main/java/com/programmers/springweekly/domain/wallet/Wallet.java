package com.programmers.springweekly.domain.wallet;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Wallet {

    private final UUID walletId;
    private final UUID customerId;
    private final UUID voucherId;

    @Builder
    private Wallet(UUID walletId, UUID customerId, UUID voucherId) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

}
