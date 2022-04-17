package org.prgms.management.wallet.entity;

import java.util.UUID;

public class Wallet {
    private final UUID walletId;
    private final UUID customerId;

    public Wallet(UUID walletId, UUID customerId) {
        this.walletId = walletId;
        this.customerId = customerId;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
