package org.prgrms.java.domain.voucher;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private final UUID voucherId;
    private final UUID ownerId;
    private final long amount;
    private final String type;
    private final boolean used;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime expiredAt;

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public long getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public boolean isUsed() {
        return used;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public VoucherDto(UUID voucherId, UUID ownerId, long amount, String type, boolean used, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.voucherId = voucherId;
        this.ownerId = ownerId;
        this.amount = amount;
        this.type = type;
        this.used = used;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }
}
