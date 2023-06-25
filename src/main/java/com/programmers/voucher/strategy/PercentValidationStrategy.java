package com.programmers.voucher.strategy;

public class PercentValidationStrategy implements VoucherValidationStrategy {
    @Override
    public void validateAmount(Integer percent) {
        if (percent < 0 || percent > 100) {
            String exceptionMessage =
                    "Percent discount must be between 0 and 100. Current input: " + percent;

            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
