package com.programmers.vouchermanagement.util;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validator {
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);
    private static final long MAX_LONG_LENGTH = String.valueOf(Long.MAX_VALUE).length();

    //messages
    private static final String INVALID_DISCOUNT_AMOUNT_MESSAGE =
            "Input should be a number greater than 0";
    private static final String INVALID_DISCOUNT_PERCENT_MESSAGE =
            "Input should be a number greater than 0 and smaller than 100";
    private static final String INVALID_DISCOUNT_VALUE_MESSAGE =
            "Input should be a number greater than 0 and smaller than 999999999999999999(length: 18)";

    public static void validateDiscountValue(VoucherType voucherType, String discountValueStr) {
        validateLongBoundary(discountValueStr);
        long discountValue = Long.parseLong(discountValueStr);
        switch (voucherType) {
            case FIXED -> validateDiscountAmount(discountValue);
            case PERCENT -> validateDiscountPercent(discountValue);
        }
    }

    private static void validateLongBoundary(String discountAmount) {
        if (MAX_LONG_LENGTH <= discountAmount.length()) {
            logger.error(INVALID_DISCOUNT_VALUE_MESSAGE);
            throw new IllegalArgumentException(INVALID_DISCOUNT_VALUE_MESSAGE);
        }
    }

    private static void validateDiscountAmount(long discountAmount) {
        if (discountAmount <= 0) {
            logger.error(INVALID_DISCOUNT_AMOUNT_MESSAGE);
            throw new IllegalArgumentException(INVALID_DISCOUNT_AMOUNT_MESSAGE);
        }
    }

    private static void validateDiscountPercent(long discountPercent) {
        if (discountPercent <= 0 || discountPercent > 100) {
            logger.error(INVALID_DISCOUNT_PERCENT_MESSAGE);
            throw new IllegalArgumentException(INVALID_DISCOUNT_PERCENT_MESSAGE);
        }
    }
}
