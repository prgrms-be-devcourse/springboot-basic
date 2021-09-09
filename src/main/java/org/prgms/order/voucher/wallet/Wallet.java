package org.prgms.order.voucher.wallet;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class Wallet {
    private final UUID walletId;
    private final UUID customerId;
    private final UUID voucherId;
    private final LocalDateTime createdAt;
    private LocalDateTime usedAt;

    public Wallet(UUID walletId, UUID customerId, UUID voucherId, LocalDateTime createdAt) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
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

    public LocalDateTime getUsedAt() {
        return usedAt;
    }
}
