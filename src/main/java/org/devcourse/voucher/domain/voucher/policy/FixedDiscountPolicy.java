package org.devcourse.voucher.domain.voucher.policy;

public class FixedDiscountPolicy extends DiscountPolicy {

    public FixedDiscountPolicy(int discountAmount) {
        super(discountAmount);
    }

    @Override
    public int discount(int targetAmount) {
        return targetAmount - providedAmount;
    }

    @Override
    protected boolean invalid(int providedAmount) {
        return providedAmount < 0 || providedAmount > 100_000;
    }
}
