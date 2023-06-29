package com.devcourse.springbootbasic.application.dto;

import com.devcourse.springbootbasic.application.constant.Message;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscountValue {

    private static final Logger logger = LoggerFactory.getLogger(DiscountValue.class);

    private final double value;

    public DiscountValue(VoucherType voucherType, double value) {
        this.value = value;
        validatePositive();
        validatePercent(voucherType);
    }

    private void validatePositive() {
        if (value < 0) {
            logger.error("Discount Value Error - {} is under 0", value);
            throw new InvalidDataException(Message.INVALID_DISCOUNT_VALUE.getMessageText());
        }
    }

    private void validatePercent(VoucherType voucherType) {
        if (voucherType.equals(VoucherType.PERCENT_DISCOUNT) && value > 100) {
            logger.error("Discount Value Error - {} is upper than 100%", value);
            throw new InvalidDataException(Message.INVALID_DISCOUNT_VALUE.getMessageText());
        }
    }

    public double getValue() {
        return value;
    }
}
