package org.devcourse.voucher.domain.voucher.policy;

public class PercentDiscountPolicy extends DiscountPolicy {

    public PercentDiscountPolicy(int discountAmount) {
        super(discountAmount);
    }

    @Override
    public int discount(int targetAmount) {
        return targetAmount - (targetAmount * providedAmount / 100);
    }

    @Override
    protected boolean invalid(int providedAmount) {
        return providedAmount < 0 || providedAmount > 100;
    }
}
