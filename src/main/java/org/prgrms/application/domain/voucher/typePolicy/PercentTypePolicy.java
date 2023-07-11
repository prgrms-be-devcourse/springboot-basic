package org.prgrms.application.domain.voucher.typePolicy;

import org.prgrms.application.domain.voucher.VoucherType;

public class PercentTypePolicy extends VoucherTypePolicy{

    public PercentTypePolicy(double discountAmount){
        super(discountAmount);
    }

    public PercentTypePolicy() {
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
