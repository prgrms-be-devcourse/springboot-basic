package com.programmers.voucher.domain;

import com.programmers.exception.AmountValueException;

public class DiscountAmount {

    private static final long MIN_AMOUNT = 1;
    private static final long MAX_AMOUNT = 1000000;

    private final long amount;

    public DiscountAmount(long amount) {
        validateDiscountAmount(amount);
        this.amount = amount;
    }

    private void validateDiscountAmount(long amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new AmountValueException();
        }
    }

    public long discount(long beforeDiscount) {
        if (discountable(beforeDiscount)) {
            return beforeDiscount - amount;
        }
        throw new AmountValueException();
    }

    public boolean discountable(long beforeDiscount) {
        if (beforeDiscount > amount) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}
