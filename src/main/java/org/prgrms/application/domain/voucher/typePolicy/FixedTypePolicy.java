package org.prgrms.application.domain.voucher.typePolicy;

import org.prgrms.application.domain.voucher.VoucherType;

public class FixedTypePolicy implements VoucherTypePolicy{

    private double discountAmount;
    public static final double MIN_DISCOUNT_VALUE = 0;// 수정

    public FixedTypePolicy(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public void validatePositive(double discountAmount) {
        if (discountAmount <= MIN_DISCOUNT_VALUE) throw new IllegalArgumentException("금액은 양수여야 합니다.");
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public double discount(double beforeDiscount) {
        validatePositive(beforeDiscount - discountAmount);
        return beforeDiscount - discountAmount;
    }
}
