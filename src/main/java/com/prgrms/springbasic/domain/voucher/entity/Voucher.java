package com.prgrms.springbasic.domain.voucher.entity;

import java.util.UUID;

public abstract class Voucher {
    protected UUID voucherId;
    protected DiscountType discountType;
    protected long discountValue;

    public static Voucher createVoucher(UUID voucherId, String discountType, long discountValue) {
        return switch (DiscountType.find(discountType)) {
            case FIXED -> new FixedAmountVoucher(voucherId, DiscountType.find(discountType), discountValue);
            case PERCENT -> new PercentDiscountVoucher(voucherId, DiscountType.find(discountType), discountValue);
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
}
