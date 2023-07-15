package com.programmers.springmission.voucher.domain;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;
import com.programmers.springmission.voucher.domain.enums.VoucherType;

public class PercentDiscountPolicy implements VoucherPolicy {

    private static final long MIN_PERCENT = 0;
    private static final long MAX_PERCENT = 100;

    private final VoucherType voucherType;

    public PercentDiscountPolicy(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long discount(long beforeDiscount, long amount) {
        validateAmount(amount);
        return beforeDiscount - (beforeDiscount * amount / 100);
    }

    @Override
    public void validateAmount(long amount) {
        if (amount <= MIN_PERCENT || amount > MAX_PERCENT) {
            throw new InvalidInputException(ErrorMessage.INVALID_DISCOUNT_AMOUNT);
        }
    }
}

