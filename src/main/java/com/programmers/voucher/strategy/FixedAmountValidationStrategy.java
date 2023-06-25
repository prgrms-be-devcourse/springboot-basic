package com.programmers.voucher.strategy;

import com.programmers.voucher.util.VoucherErrorMessages;

public class FixedAmountValidationStrategy implements VoucherValidationStrategy {
    @Override
    public void validateAmount(Integer amount) {
        if (amount <= 0) {
            String exceptionMessage = VoucherErrorMessages.INVALID_FIXED_AMOUNT + amount;

            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
