package org.devcourse.voucher.domain.voucher.policy;

public class FixedDiscountPolicy extends DiscountPolicy {

    public FixedDiscountPolicy(int discountAmount) {
        super(discountAmount);
    }

    @Override
    public int discount(int targetAmount) {
        int discountedResult = targetAmount - providedAmount;

        if (discountedResult < 0) {
            return 0;
        }
        return discountedResult;
    }

    @Override
    protected boolean invalid(int providedAmount) {
        return providedAmount < 0 || providedAmount > 100_000;
    }
}
