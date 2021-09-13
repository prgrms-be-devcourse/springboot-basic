package org.prgrms.kdt.model.wallet;

import java.time.LocalDateTime;
import java.util.UUID;

public class Wallet {
    private final UUID walletId;
    private final UUID customerId;
    private final UUID voucherId;
    private boolean isUsed;
    private final LocalDateTime createdAt;

    public Wallet(UUID walletId, UUID customerId, UUID voucherId, LocalDateTime createdAt) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
        this.isUsed = false;
    }

//    public Wallet(UUID walletId, UUID customerId, UUID voucherId, boolean isUsed,
//        LocalDateTime createdAt) {
//        this.walletId = walletId;
//        this.customerId = customerId;
//        this.voucherId = voucherId;
//        this.isUsed = isUsed;
//        this.createdAt = createdAt;
//    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Wallet{" +
            "walletId=" + walletId +
            ", customerId=" + customerId +
            ", voucherId=" + voucherId +
            ", isUsed=" + isUsed +
            ", createdAt=" + createdAt +
            '}';
    }
}
