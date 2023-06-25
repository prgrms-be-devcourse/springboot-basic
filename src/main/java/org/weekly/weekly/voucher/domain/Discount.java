package org.weekly.weekly.voucher.domain;

import org.weekly.weekly.util.DiscountType;

public interface Discount {
    long applyDiscount(long beforeAmount, long discountAmount);
    DiscountType discountType();
}
