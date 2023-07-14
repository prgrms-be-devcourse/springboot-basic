package org.weekly.weekly.voucher.domain;

public class PercentDiscount implements Discount {
    private static final int PERCENT = 100;

    @Override
    public long applyDiscount(long beforeAmount, long discountAmount) {
        return beforeAmount - beforeAmount * discountAmount / PERCENT;
    }

    @Override
    public DiscountType discountType() {
        return DiscountType.PERCENT;
    }
}
