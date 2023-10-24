package com.prgrms.vouchermanager.domain.wallet;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Wallet {
    private final UUID walletId;
    private final UUID voucherId;
    private final UUID customerId;

    public Wallet(UUID voucherId, UUID customerId) {
        this.walletId = UUID.randomUUID();
        this.voucherId = voucherId;
        this.customerId = customerId;
    }

    public Wallet(UUID walletId, UUID voucherId, UUID customerId) {
        this.walletId = walletId;
        this.voucherId = voucherId;
        this.customerId = customerId;
    }
}
