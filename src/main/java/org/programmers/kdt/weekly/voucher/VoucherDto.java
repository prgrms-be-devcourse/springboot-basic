package org.programmers.kdt.weekly.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private UUID voucherId;
    private long value;
    private LocalDateTime createdAt;

    public VoucherDto(UUID voucherId, long value, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.value = value;
        this.createdAt = createdAt.withNano(0);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getValue() {
        return value;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}