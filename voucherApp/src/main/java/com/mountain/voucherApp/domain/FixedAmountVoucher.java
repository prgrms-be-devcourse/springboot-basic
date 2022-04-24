package com.mountain.voucherApp.domain;

import static com.mountain.voucherApp.shared.constants.Message.MAX_MORE_ERROR;
import static com.mountain.voucherApp.shared.constants.Message.NEGATIVE_AMOUNT_ERROR;
import static com.mountain.voucherApp.shared.constants.Number.ZERO;

public class FixedAmountVoucher extends Voucher {

    private static final long MAX_VOUCHER_AMOUNT = 10000;

    @Override
    public boolean validate(long discountAmount) {
        if (discountAmount <= ZERO)
            throw new IllegalArgumentException(NEGATIVE_AMOUNT_ERROR);
        if (discountAmount > MAX_VOUCHER_AMOUNT)
            throw new IllegalArgumentException(MAX_MORE_ERROR + MAX_VOUCHER_AMOUNT);
        return true;
    }

    @Override
    public long discount(long beforeDiscount, long discountAmount) {
        long discountedAmount = beforeDiscount - discountAmount;
        return (discountedAmount < ZERO) ? ZERO : discountedAmount;
    }
}
