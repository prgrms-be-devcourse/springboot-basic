package com.programmers.springmission.voucher.domain;

import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;
import com.programmers.springmission.voucher.domain.enums.VoucherType;

public class FixedAmountPolicy implements VoucherPolicy {

    private static final long MIN_AMOUNT = 0;

    private final VoucherType voucherType;

    public FixedAmountPolicy(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long discount(long beforeDiscount, long amount) {
        validateAmount(amount);
        if (beforeDiscount - amount < MIN_AMOUNT) {
            return 0;
        }

        return beforeDiscount - amount;
    }

    @Override
    public void validateAmount(long amount) {
        if (amount <= MIN_AMOUNT) {
            throw new InvalidInputException(ErrorMessage.INVALID_DISCOUNT_AMOUNT);
        }
    }
}

