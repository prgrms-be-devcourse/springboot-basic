package com.programmers.domain.voucher;

import com.programmers.exception.AmountValueException;

public class DiscountAmount {

    private final long MIN_AMOUNT = 1;
    private final long MAX_AMOUNT = Integer.MAX_VALUE;

    private final long amount;

    public DiscountAmount(long amount) {
        validateDiscountAmount(amount);
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    private void validateDiscountAmount(long amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new AmountValueException();
        }
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }


}
