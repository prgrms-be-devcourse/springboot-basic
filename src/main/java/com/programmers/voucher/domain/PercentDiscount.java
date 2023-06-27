package com.programmers.voucher.domain;

import com.programmers.global.exception.DiscountValueException;

public class PercentDiscount extends Discount {

    private static final long MIN_AMOUNT = 1;
    private static final long MAX_AMOUNT = 100;

    public PercentDiscount(long percent) {
        super(VoucherType.PERCENT, percent);
        validateDiscountAmount(percent);
    }

    @Override
    public long applyDiscount(long itemPrice) {
        return itemPrice - (itemPrice * getValue() / MAX_AMOUNT);
    }

    private void validateDiscountAmount(long percent) {
        if (percent < MIN_AMOUNT || percent > MAX_AMOUNT) throw new DiscountValueException();
    }
}
