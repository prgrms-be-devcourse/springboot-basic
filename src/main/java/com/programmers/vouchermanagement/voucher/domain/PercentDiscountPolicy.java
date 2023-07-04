package com.programmers.vouchermanagement.voucher.domain;

import java.math.BigDecimal;

public class PercentDiscountPolicy extends DiscountPolicy {

    public static final int MIN_PERCENT = 1;
    public static final int MAX_PERCENT = 100;

    private final int amount;

    public PercentDiscountPolicy(int amount) {
        validationAmount(amount);
        this.amount = amount;
    }

    private void validationAmount(int amount) {
        if (MIN_PERCENT > amount || amount > MAX_PERCENT) {
            throw new IllegalArgumentException("The discount percentage must be between 1 and 100%.");
        }
    }

    @Override
    public int discount(int originalPrice) {
        int discountedAmount = calculateDiscountedAmount(originalPrice);
        return originalPrice - discountedAmount;
    }

    private int calculateDiscountedAmount(int originalPrice) {
        BigDecimal percent = BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(MAX_PERCENT));
        BigDecimal discountedAmount = percent.multiply(BigDecimal.valueOf(originalPrice));
        return discountedAmount.intValue();
    }
}
