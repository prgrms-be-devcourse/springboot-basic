package org.devcourse.voucher.domain.voucher.policy;

public class FixedDiscountPolicy extends Policy {

    public FixedDiscountPolicy(int discountAmount) {
        super(discountAmount);
    }

    @Override
    public int execute(int targetAmount) {
        return targetAmount - providedAmount;
    }

    @Override
    protected boolean invalid(int providedAmount) {
        return providedAmount < 0 || providedAmount > 100_000;
    }

    @Override
    public String toString() {
        return "%d원 할인 가능".formatted(providedAmount);
    }
}
