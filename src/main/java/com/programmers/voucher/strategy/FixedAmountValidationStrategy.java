package com.programmers.voucher.strategy;

import static com.programmers.voucher.util.VoucherErrorMessages.INVALID_FIXED_AMOUNT;

public class FixedAmountValidationStrategy implements VoucherValidationStrategy {
    @Override
    public void validateAmount(Integer amount) {
        if (amount <= 0) {
            String exceptionMessage =
                    INVALID_FIXED_AMOUNT + " Current input: " + amount;

            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
