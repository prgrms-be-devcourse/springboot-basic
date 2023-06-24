package com.programmers.voucher.domain;

import com.programmers.global.exception.AmountValueException;

public class DiscountAmount implements Discount {

    private static final long MIN_AMOUNT = 1;
    private static final long MAX_AMOUNT = 1_000_000;

    private final long amount;

    public DiscountAmount(long amount) {
        validateDiscountAmount(amount);
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }

    @Override
    public long discount(long beforeDiscount) {
        if (discountable(beforeDiscount)) {
            return beforeDiscount - amount;
        }
        throw new AmountValueException();
    }

    private boolean discountable(long beforeDiscount) {
        return beforeDiscount > amount;
    }

    private void validateDiscountAmount(long amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new AmountValueException();
        }
    }
}
