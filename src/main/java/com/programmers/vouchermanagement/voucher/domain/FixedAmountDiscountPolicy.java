package com.programmers.vouchermanagement.voucher.domain;

public class FixedAmountDiscountPolicy extends DiscountPolicy {

    public static final int MIN_AMOUNT = 1;

    private final int amount;

    public FixedAmountDiscountPolicy(int amount) {
        validationAmount(amount);
        this.amount = amount;
    }

    private void validationAmount(int amount) {
        if (amount < MIN_AMOUNT) {
            throw new IllegalArgumentException("The minimum discount amount is 1.");
        }
    }

    @Override
    public int discount(int originalPrice) {
        if (amount > originalPrice) {
            return 0;
        }
        return originalPrice - amount;
    }
}
