package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.exception.InvalidDiscountAmountException;

public class FixedAmountDiscountPolicy extends DiscountPolicy {

    public static final int MIN_AMOUNT = 1;

    public FixedAmountDiscountPolicy(int amount) {
        super(amount);
    }

    @Override
    void validateAmount(int amount) {
        if (amount < MIN_AMOUNT) {
            throw new InvalidDiscountAmountException("고정할인금액은 최소 1원 이상이여야 합니다.");
        }
    }

    @Override
    public DiscountType getType() {
        return DiscountType.FIX;
    }

    @Override
    public int discount(int originalPrice) {
        if (getAmount() > originalPrice) {
            return 0;
        }
        return originalPrice - getAmount();
    }
}
