package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.voucher.exception.InvalidDiscountException;

public class PercentDiscountPolicy extends DiscountPolicy {
    private static final double MIN_AMOUNT = 0.0;
    private static final double MAX_AMOUNT = 100.0;

    public PercentDiscountPolicy(double amount) {
        super(amount);
        validate(amount);
    }

    private void validate(double amount) {
        if (MAX_AMOUNT < amount || amount < MIN_AMOUNT) throw new InvalidDiscountException("올바르지 않은 할인 퍼센트입니다.");
    }

    @Override
    public double applyDiscount(double beforeDiscount) {
        return beforeDiscount * ((100 - amount) / 100);
    }
}
