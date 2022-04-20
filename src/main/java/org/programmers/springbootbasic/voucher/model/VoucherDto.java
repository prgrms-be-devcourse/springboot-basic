package org.programmers.springbootbasic.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private final UUID voucherId;
    private long value;
    private final LocalDateTime createdAt;

    public VoucherDto(UUID voucherId, long value, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.value = value;
        this.createdAt = createdAt;
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
