package com.mountain.voucherApp.domain;

import static com.mountain.voucherApp.shared.constants.ErrorMessage.*;
import static com.mountain.voucherApp.shared.constants.VoucherConstant.COMMON_MIN_AMOUNT;

public class FixedAmountVoucher extends Voucher {

    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private static final int MIN_VOUCHER_AMOUNT = 0;

    @Override
    public boolean validate(long discountAmount) {
        if (discountAmount <= MIN_VOUCHER_AMOUNT)
            throw new IllegalArgumentException(NEGATIVE_AMOUNT_ERROR);
        if (discountAmount > MAX_VOUCHER_AMOUNT)
            throw new IllegalArgumentException(MAX_MORE_ERROR + MAX_VOUCHER_AMOUNT);
        return true;
    }

    @Override
    public long discount(long beforeDiscount, long discountAmount) {
        long discountedAmount = beforeDiscount - discountAmount;
        return (discountedAmount < COMMON_MIN_AMOUNT) ? COMMON_MIN_AMOUNT : discountedAmount;
    }
}
