package com.programmers.voucher.domain;

import java.text.MessageFormat;

public class PercentDiscount extends Discount {

    private static final long MIN_PERCENT = 1;
    private static final long MAX_PERCENT = 100;
    private static final String WRONG_PERCENT_RANGE_MESSAGE =
            MessageFormat.format("[ERROR] 값의 유효범위는 {0}이상 {1}이하입니다.",
                    MIN_PERCENT, MAX_PERCENT);

    public PercentDiscount(long percent) {
        super(VoucherType.PERCENT, percent);
        validateDiscountAmount(percent);
    }

    @Override
    public long applyDiscount(long itemPrice) {
        return itemPrice - (itemPrice * getValue() / MAX_PERCENT);
    }

    private void validateDiscountAmount(long percent) {
        if (percent < MIN_PERCENT || percent > MAX_PERCENT)
            throw new IllegalArgumentException(WRONG_PERCENT_RANGE_MESSAGE);
    }
}
