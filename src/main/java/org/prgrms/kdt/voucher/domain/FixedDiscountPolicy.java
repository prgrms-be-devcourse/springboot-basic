package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.voucher.exception.InvalidDiscountException;

public class FixedDiscountPolicy extends DiscountPolicy {
    private static final double MIN_AMOUNT = 0.0;

    public FixedDiscountPolicy(double amount) {
        super(amount);
        validate(amount);
    }

    private void validate(double amount) {
        if (amount < MIN_AMOUNT) throw new InvalidDiscountException("올바르지 않은 할인 금액입니다.");
    }

    @Override
    public double applyDiscount(double beforeDiscount) {
        return beforeDiscount - amount;
    }
}
