package com.programmers.voucher.domain;

public abstract class Discount {

    private final DiscountType discountType;

    protected Discount(DiscountType discountType) {
        this.discountType = discountType;
    }

    public static Discount of(DiscountType discountType, long value) {
        return switch (discountType) {
            case FIXED -> new FixedDiscount(value);
            case PERCENT -> new PercentDiscount(value);
        };
    }

    public abstract long applyDiscount(long beforeDiscount);

    public abstract long getAmount();

    public DiscountType getVoucherType() {
        return discountType;
    }
}
