package com.programmers.vouchermanagement.util;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;

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

    public static void validateDiscountValue(CreateVoucherRequest request) {
        switch (request.voucherType()) {
            case FIXED -> validateDiscountAmount(request.discountValue());
            case PERCENT -> validateDiscountPercent(request.discountValue());
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
