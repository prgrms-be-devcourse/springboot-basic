package com.programmers.vouchermanagement.voucher.domain;

public class FixedAmountDiscountPolicy implements DiscountPolicy {

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
    public int getAmount() {
        return amount;
    }

    @Override
    public DiscountType getType() {
        return DiscountType.FIX;
    }

    @Override
    public int discount(int originalPrice) {
        if (amount > originalPrice) {
            return 0;
        }
        return originalPrice - amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixedAmountDiscountPolicy that = (FixedAmountDiscountPolicy) o;

        return amount == that.amount;
    }

    @Override
    public int hashCode() {
        return amount;
    }
}
