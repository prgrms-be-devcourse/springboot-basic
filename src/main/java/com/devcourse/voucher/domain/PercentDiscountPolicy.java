package com.devcourse.voucher.domain;

import java.math.BigDecimal;

import static com.devcourse.voucher.domain.VoucherType.*;

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

    @Override
    public VoucherType getType() {
        return PERCENT;
    }

    @Override
    public BigDecimal discountAmount() {
        return BigDecimal.valueOf(discountRate);
    }

    private double calculate(long price) {
        return price * ((MAX_RATE - discountRate) / MAX_RATE);
    }
}
