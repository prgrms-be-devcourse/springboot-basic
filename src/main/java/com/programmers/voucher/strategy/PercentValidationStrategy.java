package com.programmers.voucher.strategy;

import static com.programmers.voucher.util.VoucherErrorMessages.INVALID_PERCENT_DISCOUNT;

public class PercentValidationStrategy implements VoucherValidationStrategy {
    @Override
    public void validateAmount(Integer percent) {
        if (percent < 0 || percent > 100) {
            String exceptionMessage =
                    INVALID_PERCENT_DISCOUNT + " CurrentInput: " + percent;

            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
