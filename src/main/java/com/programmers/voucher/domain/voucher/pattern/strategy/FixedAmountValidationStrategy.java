package com.programmers.voucher.domain.voucher.pattern.strategy;

import com.programmers.voucher.global.util.VoucherErrorMessages;

public class FixedAmountValidationStrategy implements VoucherValidationStrategy {
    @Override
    public void validateAmount(long amount) {
        if (amount <= 0) {
            String exceptionMessage = VoucherErrorMessages.INVALID_FIXED_AMOUNT + amount;

            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
