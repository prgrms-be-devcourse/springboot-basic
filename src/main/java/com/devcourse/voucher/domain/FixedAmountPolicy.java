package com.devcourse.voucher.domain;

import java.math.BigDecimal;

import static com.devcourse.voucher.domain.VoucherType.*;

public class FixedAmountPolicy implements DiscountPolicy {
    private static final int ZERO = 0;

    private final int discountAmount;

    public FixedAmountPolicy(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public BigDecimal discount(long price) {
        long result = calculate(price);
        return BigDecimal.valueOf(result);
    }

    @Override
    public VoucherType getType() {
        return FIXED;
    }

    @Override
    public BigDecimal discountAmount() {
        return BigDecimal.valueOf(discountAmount);
    }

    private long calculate(long price) {
        long result = price - discountAmount;
        return result < ZERO ? ZERO : result;
    }
}
