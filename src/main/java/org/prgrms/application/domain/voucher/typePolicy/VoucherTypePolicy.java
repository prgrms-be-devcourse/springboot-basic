package org.prgrms.application.domain.voucher.typePolicy;

import org.prgrms.application.domain.voucher.VoucherType;

public abstract class VoucherTypePolicy {
    public static final double MAX_DISCOUNT_VALUE = 100;
    public static final double MIN_DISCOUNT_VALUE = 0;

    public VoucherTypePolicy(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public VoucherTypePolicy() {}

    protected double discountAmount;

    protected abstract void validatePositive(double discountAmount);

    public abstract VoucherType getVoucherType();

    public abstract double discount(double beforeDiscount);
}
