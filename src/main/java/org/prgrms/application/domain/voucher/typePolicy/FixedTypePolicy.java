package org.prgrms.application.domain.voucher.typePolicy;

import org.prgrms.application.domain.voucher.VoucherType;

public class FixedTypePolicy extends VoucherTypePolicy{

    public FixedTypePolicy(double discountAmount) {
        super(discountAmount);
    }

    public FixedTypePolicy() {
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
