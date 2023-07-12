package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.exception.VoucherErrorCode;
import com.programmers.vouchermanagement.voucher.exception.VoucherException;

public class FixedAmountDiscountPolicy extends DiscountPolicy {

    public static final int MIN_AMOUNT = 1;

    public FixedAmountDiscountPolicy(int amount) {
        super(amount);
    }

    @Override
    void validateAmount(int amount) {
        if (amount < MIN_AMOUNT) {
            throw new VoucherException(VoucherErrorCode.INVALID_FIX_AMOUNT);
        }
    }

    @Override
    public DiscountType getType() {
        return DiscountType.FIX;
    }

    @Override
    public int discount(int originalPrice) {
        if (getAmount() > originalPrice) {
            return 0;
        }
        return originalPrice - getAmount();
    }
}
