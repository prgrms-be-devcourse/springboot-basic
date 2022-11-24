package com.example.weeklymission3.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final String type;
    private final int discount;
    private final LocalDateTime createdAt;

    public Voucher(UUID voucherId, String type, int discount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.type = type;
        this.discount = discount;
        this.createdAt = createdAt;
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
}
