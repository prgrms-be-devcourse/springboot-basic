package org.weekly.weekly.voucher.domain;

public class FixedDiscount implements Discount {
    @Override
    public long applyDiscount(long beforeAmount, long discountAmount) {
        return beforeAmount - discountAmount;
    }

    @Override
    public DiscountType discountType() {
        return DiscountType.FIXED;
    }
}
