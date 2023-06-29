package com.programmers.voucher.domain;

import java.text.MessageFormat;

public class FixedDiscount extends Discount {

    private static final long MIN_AMOUNT = 1;
    private static final long MAX_AMOUNT = 1_000_000_000;

    private final long amount;

    private static final String WRONG_AMOUNT_RANGE_MESSAGE =
            MessageFormat.format("[ERROR] 값의 유효범위는 {0}이상 {1}이하입니다.",
                    MIN_AMOUNT, MAX_AMOUNT);

    public FixedDiscount(long amount) {
        super(VoucherType.FIXED);
        validateDiscountAmount(amount);
        this.amount = amount;
    }

    @Override
    public long applyDiscount(long itemPrice) {
        if (itemPrice < amount) return 0;
        return itemPrice - amount;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    private void validateDiscountAmount(long amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT)
            throw new IllegalArgumentException(WRONG_AMOUNT_RANGE_MESSAGE);
    }
}
