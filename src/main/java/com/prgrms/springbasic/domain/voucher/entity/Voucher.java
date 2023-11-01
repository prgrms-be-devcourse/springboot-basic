package com.prgrms.springbasic.domain.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    protected UUID voucherId;
    protected DiscountType discountType;
    protected long discountValue;

    protected LocalDateTime createdAt;

    public static Voucher createVoucher(UUID voucherId, String discountType, long discountValue, LocalDateTime createdAt) {
        return switch (DiscountType.find(discountType)) {
            case FIXED -> new FixedAmountVoucher(voucherId, DiscountType.find(discountType), discountValue, createdAt);
            case PERCENT -> new PercentDiscountVoucher(voucherId, DiscountType.find(discountType), discountValue, createdAt);
        };
    }

    abstract long discount(long beforeDiscount);

    public abstract void update(long updateDiscountValue);

    public UUID getVoucherId() {
        return voucherId;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
