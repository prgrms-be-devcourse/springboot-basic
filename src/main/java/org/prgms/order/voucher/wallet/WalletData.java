package org.prgms.order.voucher.wallet;

import java.time.LocalDateTime;
import java.util.UUID;

public class WalletData {
    private final UUID walletId;
    private UUID customerId;
    private UUID voucherId;
    private LocalDateTime createdAt;
    private LocalDateTime usedAt;

    public WalletData(UUID walletId, LocalDateTime usedAt) {
        this.walletId = walletId;
    }

    public WalletData(UUID walletId, UUID customerId, UUID voucherId) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public WalletData(UUID walletId, UUID customerId, UUID voucherId, LocalDateTime createdAt) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
    }

    public WalletData(UUID walletId, UUID customerId, UUID voucherId, LocalDateTime createdAt, LocalDateTime usedAt) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
        this.usedAt = usedAt;
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
