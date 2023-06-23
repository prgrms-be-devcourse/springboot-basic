package com.programmers.voucher.domain;

import com.programmers.exception.AmountValueException;

public class DiscountPercent {

    private final long MIN_AMOUNT = 1;
    private final long MAX_AMOUNT = 100;

    private final long percent;

    public DiscountPercent(long percent) {
        validateDiscountAmount(percent);
        this.percent = percent;
    }

    public long getPercent() {
        return percent;
    }

    private void validateDiscountAmount(long percent) {
        if (percent < MIN_AMOUNT || percent > MAX_AMOUNT) {
            throw new AmountValueException();
        }
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / MAX_AMOUNT);
    }

    @Override
    public String toString() {
        return String.valueOf(percent);
    }
}
