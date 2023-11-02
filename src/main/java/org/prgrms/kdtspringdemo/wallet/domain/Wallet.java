package org.prgrms.kdtspringdemo.wallet.domain;

import java.util.UUID;
import java.util.List;

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

    public String toString() {
        return "=======================\n" +
                "[walletId] : " + walletId + "\n" +
                "[customerId] : " + customerId + "\n";
    }
}
