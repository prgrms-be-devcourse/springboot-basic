package org.prgms.order.wallet;


import java.time.LocalDateTime;
import java.util.UUID;

public class Wallet {
    private final UUID walletId;
    private final UUID customerId;
    private final UUID voucherId;
    private final LocalDateTime createdAt;
    private boolean usedAt;

    public Wallet(WalletData walletData) {
        this.walletId = walletData.getWalletId();
        this.customerId = walletData.getCustomerId();
        this.voucherId = walletData.getVoucherId();
        this.createdAt = walletData.getCreatedAt();
        this.usedAt = walletData.getUsedAt();
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

    public boolean getUsedAt() {
        return usedAt;
    }
}
