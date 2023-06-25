package com.programmers.voucher.strategy;

public class FixedAmountValidationStrategy implements VoucherValidationStrategy {
    @Override
    public void validateAmount(Integer amount) {
        if (amount <= 0) {
            String exceptionMessage =
                    "Fixed amount must be positive. Current input: " + amount;

            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
