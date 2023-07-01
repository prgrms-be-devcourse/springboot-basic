package com.programmers.springmission.voucher.domain;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;

public class FixedAmountPolicy implements VoucherPolicy {

    private static final long MIN_AMOUNT = 0;

    private final long amount;

    public FixedAmountPolicy(long amount) {
        validate(amount);
        this.amount = amount;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    private void validate(long amount) {
        if (amount <= MIN_AMOUNT) {
            throw new InvalidInputException(ErrorMessage.INVALID_DISCOUNT_AMOUNT);
        }
    }
}

