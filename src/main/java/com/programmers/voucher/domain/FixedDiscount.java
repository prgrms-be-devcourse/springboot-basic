package com.programmers.voucher.domain;

import com.programmers.global.exception.DiscountValueException;

public class FixedDiscount extends Discount {

    private static final long MIN_AMOUNT = 1;
    private static final long MAX_AMOUNT = 1_000_000_000;

    public FixedDiscount(long amount) {
        super(VoucherType.FIXED, amount);
        validateDiscountAmount(amount);
    }

    @Override
    public long applyDiscount(long itemPrice) {
        if (itemPrice < getValue()) return 0;
        return itemPrice - getValue();
    }

    private void validateDiscountAmount(long amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new DiscountValueException();
        }
    }
}
