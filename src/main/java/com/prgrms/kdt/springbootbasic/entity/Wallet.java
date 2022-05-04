package com.prgrms.kdt.springbootbasic.entity;

import java.util.UUID;

public class Wallet {
    private final UUID walletId;
    private final UUID customerId;
    private final UUID voucherId;

    public Wallet(UUID walletId, UUID customerId, UUID voucherId) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
