package org.prgrms.kdtspringdemo.wallet;

import java.time.LocalDateTime;
import java.util.UUID;

public class Wallet {
    private final UUID walletId;
    private final UUID customerId;
    private UUID voucherId;
    private final LocalDateTime createdAt;

    public Wallet(UUID walletId,UUID customerId, LocalDateTime createdAt) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.createdAt = createdAt;
    }

    public Wallet(UUID walletId, UUID customerId, UUID voucherId, LocalDateTime createdAt) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    private void validate(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
    }
}
