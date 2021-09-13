package org.prgms.order.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherData {
    private final UUID voucherId;
    private VoucherIndexType type;
    private long amount;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    public VoucherData(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public VoucherData(UUID voucherId, LocalDateTime expiredAt) {
        this.voucherId = voucherId;
        this.expiredAt = expiredAt;
    }

    public VoucherData(UUID voucherId, long amount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public VoucherData(UUID voucherId, long amount, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}

