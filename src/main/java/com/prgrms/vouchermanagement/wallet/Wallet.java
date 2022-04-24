package com.prgrms.vouchermanagement.wallet;

import java.util.UUID;

public class Wallet {
    private UUID walletId;
    private UUID customerId;
    private UUID voucherId;

    private Wallet(UUID walletId, UUID customerId, UUID voucherId) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public static Wallet of(UUID walletId, UUID customerId, UUID voucherId) {
        return new Wallet(walletId, customerId, voucherId);
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