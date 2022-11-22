package com.programmers.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherEntity {
    private final UUID voucherId;
    private final String type;
    private int discount;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiredAt;

    public VoucherEntity(UUID voucherId, String type, int discount, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.voucherId = voucherId;
        this.type = type;
        this.discount = discount;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getType() {
        return type;
    }

    public int getDiscount() {
        return discount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void changeDiscount(int discount) {
        this.discount = discount;
    }
}

