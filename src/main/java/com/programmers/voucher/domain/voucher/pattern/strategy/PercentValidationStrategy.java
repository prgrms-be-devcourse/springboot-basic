package com.programmers.voucher.domain.voucher.pattern.strategy;

import com.programmers.voucher.global.util.VoucherErrorMessages;

public class PercentValidationStrategy implements VoucherValidationStrategy {
    @Override
    public void validateAmount(long percent) {
        if (percent < 0 || percent > 100) {
            String exceptionMessage = VoucherErrorMessages.INVALID_PERCENT_DISCOUNT + " Current input: " + percent;

            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
