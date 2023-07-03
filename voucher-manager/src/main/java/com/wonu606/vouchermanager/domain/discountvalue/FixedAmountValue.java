package com.wonu606.vouchermanager.domain.discountvalue;

public class FixedAmountValue extends DiscountValue {

    public FixedAmountValue(double fixedAmount) {
        super(fixedAmount);
    }

    @Override
    protected void validateValue(double fixedAmount) {
        if (isNotPositive(fixedAmount)) {
            throw new IllegalArgumentException("할인할 금액은 양수여야 합니다.");
        }
    }

    private boolean isNotPositive(double fixedAmount) {
        return fixedAmount < 0;
    }
}
