package com.wonu606.vouchermanager.domain.voucher.discountvalue;

public class PercentageDiscountValue extends DiscountValue {

    public PercentageDiscountValue(double percentage) {
        super(percentage);
    }

    @Override
    protected void validateValue(double percentage) {
        if (isPercentageInRange(percentage)) {
            throw new IllegalArgumentException("할인율은 0에서 100 사이여야 합니다.");
        }
    }

    private boolean isPercentageInRange(double percentage) {
        return percentage < 0 || 100 < percentage;
    }
}
