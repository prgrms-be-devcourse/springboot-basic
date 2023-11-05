package com.programmers.vouchermanagement.voucher.domain.vouchertype;

import java.text.MessageFormat;

public class FixedAmountVoucherType extends VoucherType {
    private static final String NAME = "FIXED";
    private static final FixedAmountVoucherType INSTANCE = new FixedAmountVoucherType();
    private static final long MAX_DISCOUNT_VALUE = 100000000;

    private FixedAmountVoucherType() {
    }

    public static VoucherType getInstance() {
        return INSTANCE;
    }

    @Override
    public void validateDiscountValue(long discountValue) {
        if (discountValue < MIN_DISCOUNT_VALUE || MAX_DISCOUNT_VALUE < discountValue) {
            throw new IllegalArgumentException(MessageFormat.format("The discount price({0}) is not appropriate at FixedAmountVoucher.", discountValue));
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}