package com.mountain.voucherApp.domain;

import static com.mountain.voucherApp.shared.constants.ErrorMessage.*;
import static com.mountain.voucherApp.shared.constants.VoucherConstant.ZERO_P_ZERO_ONE;

public class PercentDiscountVoucher extends Voucher {

    private static final long MAX_VOUCHER_AMOUNT = 100;
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
        double minusAmount = Math.round(discountAmount * ZERO_P_ZERO_ONE * beforeDiscount);
        long result = beforeDiscount - (long)minusAmount;
        return result;
    }
}
