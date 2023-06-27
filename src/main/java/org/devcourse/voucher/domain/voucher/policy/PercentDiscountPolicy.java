package org.devcourse.voucher.domain.voucher.policy;

public class PercentDiscountPolicy extends Policy {

    public PercentDiscountPolicy(int discountAmount) {
        super(discountAmount);
    }

    @Override
    public int execute(int targetAmount) {
        return targetAmount - (targetAmount * providedAmount / 100);
    }

    @Override
    protected boolean invalid(int providedAmount) {
        return providedAmount < 0 || providedAmount > 100;
    }

    @Override
    public String toString() {
        return "%d퍼센트 할인 가능".formatted(providedAmount);
    }
}
