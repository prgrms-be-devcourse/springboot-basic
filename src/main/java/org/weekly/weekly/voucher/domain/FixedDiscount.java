package org.weekly.weekly.voucher.domain;

import org.weekly.weekly.util.DiscountType;

public class FixedDiscount implements Discount{

    @Override
    public long applyDiscount(long beforeAmount, long discountAmount) {
        return beforeAmount - discountAmount;
    }
}
