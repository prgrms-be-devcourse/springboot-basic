package com.prgrms.vouchermanagement.wallet;

import java.time.LocalDateTime;
import java.util.UUID;

public class Wallet {
    private final UUID walletId;
    private final UUID customerId;
    private final UUID voucherId;

    private final LocalDateTime  createdAt;

    public Wallet(UUID walletId, UUID customerId, UUID voucherId, LocalDateTime createdAt) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
    }

    public static Wallet of(UUID walletId, UUID customerId, UUID voucherId) {
        return new Wallet(walletId, customerId, voucherId, LocalDateTime.now());
    }

    public static Wallet of(UUID walletId, UUID customerId, UUID voucherId, LocalDateTime createdAt) {
        return new Wallet(walletId, customerId, voucherId, createdAt);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}