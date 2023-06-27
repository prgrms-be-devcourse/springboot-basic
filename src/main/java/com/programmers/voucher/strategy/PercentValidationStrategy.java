package com.programmers.voucher.strategy;

import com.programmers.voucher.util.VoucherErrorMessages;

public class PercentValidationStrategy implements VoucherValidationStrategy {
    @Override
    public void validateAmount(long percent) {
        if (percent < 0 || percent > 100) {
            String exceptionMessage = VoucherErrorMessages.INVALID_PERCENT_DISCOUNT + percent;

            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
