package org.programmers.weekly.mission.domain.wallet;

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

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", customerId=" + customerId +
                ", voucherId=" + voucherId +
                '}';
    }
}
