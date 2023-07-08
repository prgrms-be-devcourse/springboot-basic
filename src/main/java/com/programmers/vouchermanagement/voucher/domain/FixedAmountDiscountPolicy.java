package com.programmers.vouchermanagement.voucher.domain;

public class FixedAmountDiscountPolicy extends DiscountPolicy {

    public static final int MIN_AMOUNT = 1;

    public FixedAmountDiscountPolicy(int amount) {
        super(amount);
    }

    @Override
    void validateAmount(int amount) {
        if (amount < MIN_AMOUNT) {
            throw new IllegalArgumentException("The minimum discount amount is 1.");
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
