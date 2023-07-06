package com.devcourse.voucher.domain;

import java.math.BigDecimal;

class PercentDiscountPolicy implements DiscountPolicy {
    private static final int MAX_RATE = 100;

    PercentDiscountPolicy() { }

    @Override
    public BigDecimal discount(long target, int discount) {
        double result = target * ((double) (MAX_RATE - discount) / MAX_RATE);
        return BigDecimal.valueOf(result);
    }
}
