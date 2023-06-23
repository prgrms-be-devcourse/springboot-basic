package com.programmers.voucher.domain;

import com.programmers.exception.AmountValueException;

public class DiscountPercent implements Discount{

    private static final long MIN_AMOUNT = 1;
    private static final long MAX_AMOUNT = 100;
    private static final double HUNDRED = 100.0;

    private final long percent;

    public DiscountPercent(long percent) {
        validateDiscountAmount(percent);
        this.percent = percent;
    }

    @Override
    public long getValue() {
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (1 - (percent / HUNDRED)));
    }

    private void validateDiscountAmount(long percent) {
        if (percent < MIN_AMOUNT || percent >= MAX_AMOUNT) {
            throw new AmountValueException();
        }
    }

    @Override
    public String toString() {
        return String.valueOf(percent);
    }
}
