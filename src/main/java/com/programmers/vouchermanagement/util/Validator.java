package com.programmers.vouchermanagement.util;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;

public class Validator {
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    //constants
    private static final int COMPARATOR_FLAG = 0;
    private static final BigDecimal MAX_PERCENT = BigDecimal.valueOf(100);

    //messages
    private static final String INVALID_DISCOUNT_AMOUNT_MESSAGE =
            "Input should be a number greater than 0";
    private static final String INVALID_DISCOUNT_PERCENT_MESSAGE =
            "Input should be a number greater than 0 and smaller than 100";
    private static final String NAME_LENGTH_EXCESSIVE = "Name is too long.";
    private static final int MAX_NAME_LENGTH = 25;

    public static void validateDiscountValue(BigDecimal discountValue, VoucherType voucherType) {
        switch (voucherType) {
            case FIXED -> validateDiscountAmount(discountValue);
            case PERCENT -> validateDiscountPercent(discountValue);
        }
    }

    public static void validateCustomerName(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(NAME_LENGTH_EXCESSIVE);
        }
    }

    private static void validateDiscountAmount(BigDecimal discountAmount) {
        if (discountAmount.compareTo(BigDecimal.ZERO) <= COMPARATOR_FLAG) {
            logger.error(INVALID_DISCOUNT_AMOUNT_MESSAGE);
            throw new IllegalArgumentException(INVALID_DISCOUNT_AMOUNT_MESSAGE);
        }
    }

    private static void validateDiscountPercent(BigDecimal discountPercent) {
        if (discountPercent.compareTo(BigDecimal.ZERO) <= COMPARATOR_FLAG || discountPercent.compareTo(MAX_PERCENT) > COMPARATOR_FLAG) {
            logger.error(INVALID_DISCOUNT_PERCENT_MESSAGE);
            throw new IllegalArgumentException(INVALID_DISCOUNT_PERCENT_MESSAGE);
        }
    }
}
