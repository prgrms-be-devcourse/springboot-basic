package org.weekly.weekly.voucher.domain;

public interface Discount {
    long applyDiscount(long beforeAmount, long discountAmount);

    DiscountType discountType();
}
