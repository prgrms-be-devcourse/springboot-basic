package com.devcourse.voucher;

import java.math.BigDecimal;

public class PercentDiscountPolicy implements DiscountPolicy {
    private static final int MAX_RATE = 100;
    private final double discountRate;

    public PercentDiscountPolicy(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public BigDecimal discount(long price) {
        double result = calculate(price);
        return BigDecimal.valueOf(result);
    }

    private double calculate(long price) {
        return price * ((MAX_RATE - discountRate) / MAX_RATE);
    }
}
