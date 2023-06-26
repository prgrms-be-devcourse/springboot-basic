package com.programmers.voucher.domain;

import com.programmers.global.exception.AmountValueException;

public class FixedDiscount extends Discount {

    private static final long MIN_AMOUNT = 1;
    private static final long MAX_AMOUNT = 1_000_000;

    private final VoucherType voucherType = VoucherType.FIXED;

    public FixedDiscount(long amount) {
        super(amount);
        validateDiscountAmount(amount);
    }

    @Override
    public long applyDiscount(long itemPrice) {
        if (discountable(itemPrice)) {
            return itemPrice - getValue();
        }
        throw new AmountValueException();
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    private boolean discountable(long itemPrice) {
        return itemPrice >= this.getValue();
    }

    private void validateDiscountAmount(long amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new AmountValueException();
        }
    }
}
