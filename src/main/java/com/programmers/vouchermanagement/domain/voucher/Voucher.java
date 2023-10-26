package com.programmers.vouchermanagement.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    protected final UUID id;
    protected final String name;
    protected final float discountAmount;
    protected final LocalDateTime createdAt;

    protected Voucher(UUID id, String name, float discountAmount, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public abstract VoucherType getVoucherType();

    public abstract float discount(float beforeDiscount);

    public abstract void validateDiscountAmount(float discountAmount);

    public abstract String joinInfo(String separator);
}
