package com.devcourse.voucher.domain;

import java.math.BigDecimal;

class FixedAmountPolicy implements DiscountPolicy {
    private static final int ZERO = 0;

    FixedAmountPolicy() { }

    @Override
    public BigDecimal discount(long target, int discount) {
        long result = target - discount;
        return BigDecimal.valueOf(result < ZERO ? ZERO : result);
    }
}
