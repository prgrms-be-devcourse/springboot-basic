package org.programmers.kdt.weekly.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherDto(UUID voucherId, int value, LocalDateTime createdAt) {

    public VoucherDto(UUID voucherId, int value, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.value = value;
        this.createdAt = createdAt.withNano(0);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getValue() {
        return value;
    }
}