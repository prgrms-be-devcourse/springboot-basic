package com.programmers.vouchermanagement.voucher.domain.vouchertype;

import java.text.MessageFormat;

public class PercentVoucherType extends VoucherType {
    private static final String NAME = "PERCENT";
    private static final PercentVoucherType INSTANCE = new PercentVoucherType();
    private static final long MAX_DISCOUNT_VALUE = 100;

    private PercentVoucherType() {
    }

    public static VoucherType getInstance() {
        return INSTANCE;
    }

    @Override
    public void validateDiscountValue(long discountValue) {
        if (discountValue < MIN_DISCOUNT_VALUE || discountValue > MAX_DISCOUNT_VALUE) {
            throw new IllegalArgumentException(MessageFormat.format("The discount price({0}) is not appropriate at PercentVoucher.", discountValue));
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
