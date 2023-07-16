package org.prgrms.application.domain.voucher.typePolicy;

import org.prgrms.application.domain.voucher.VoucherType;

public class PercentTypePolicy implements VoucherTypePolicy{

    public static final double MAX_DISCOUNT_VALUE = 100;
    public static final double MIN_DISCOUNT_VALUE = 0;

    private double discountAmount;

    public PercentTypePolicy(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public void validatePositive(double discountAmount) {
        if (discountAmount <= MIN_DISCOUNT_VALUE || discountAmount >= MAX_DISCOUNT_VALUE) {
            throw new IllegalArgumentException("잘못된 입력 범위입니다.");
        }
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public double discount(double beforeDiscount) {
        return beforeDiscount * (discountAmount / 100);
    }
}
