package org.programmers.kdt.weekly.voucherWallet.model;


import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherWallet {

    private final UUID walletId;
    private final UUID customerId;
    private final UUID voucherId;
    private final LocalDateTime createdAt;
    private final LocalDateTime expirationAt;

    public VoucherWallet(UUID walletId, UUID customerId, UUID voucherId,
        LocalDateTime createdAt, LocalDateTime expirationAt) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
        this.expirationAt = expirationAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return
            "walletId: " + walletId +
                ", customerId: " + customerId +
                ", voucherId: " + voucherId +
                ", createdAt: " + createdAt +
                ", expirationAt: " + expirationAt;
    }

    public String serializeVoucherWallet() {
        return walletId + "," + customerId + "," + voucherId + "," + createdAt + "," + expirationAt;
    }
}