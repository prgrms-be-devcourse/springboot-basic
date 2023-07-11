package com.example.voucher.domain;

import static com.example.voucher.constant.ExceptionMessage.*;

public class DiscountPolicy {

    private static final int PERCENT_DIVISOR = 100;

    public Long discount(Voucher.Type type, Long originalAmount, Long discountValue) {
        return switch (type) {
            case FIXED_AMOUNT_DISCOUNT -> calculateAmountDiscount(originalAmount, discountValue);
            case PERCENT_DISCOUNT -> calculatePercentDiscount(originalAmount, discountValue);
        };
    }

    public Long calculateAmountDiscount(Long originalAmount, Long discountAmount) {
        validateGreaterThan(originalAmount, discountAmount);

        return originalAmount - discountAmount;
    }

    public Long calculatePercentDiscount(Long originalAmount, Long discountPercentValue) {
        double discountPercent = discountPercentValue / PERCENT_DIVISOR;
        double discountAmount = originalAmount * discountPercent;
        long discountedAmount = originalAmount - (long)discountAmount;

        return discountedAmount;
    }

    private void validateGreaterThan(long value, long threshold) {
        if (value <= threshold) {
            throw new IllegalArgumentException(
                String.format("{} {}", FORMAT_ERROR_GREATER_THAN_CONSTRAINT, threshold));
        }
    }

}
