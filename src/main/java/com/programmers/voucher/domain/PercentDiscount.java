package com.programmers.voucher.domain;

import com.programmers.global.exception.AmountValueException;

public class PercentDiscount extends Discount {

    private static final long MIN_AMOUNT = 1;
    private static final long MAX_AMOUNT = 100;

    private final VoucherType voucherType = VoucherType.PERCENT;

    public PercentDiscount(long percent) {
        super(percent);
        validateDiscountAmount(percent);
    }

    @Override
    public long applyDiscount(long itemPrice) {
        return itemPrice - (itemPrice * getValue() / MAX_AMOUNT);
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    private void validateDiscountAmount(long percent) {
        if (percent < MIN_AMOUNT || percent > MAX_AMOUNT) throw new AmountValueException();
    }
}
