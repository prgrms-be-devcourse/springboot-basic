package com.devcourse.springbootbasic.application.voucher.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

public class Price {

    private final double value;

    public Price(double value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(double value) {
        if (value < 0) {
            throw new InvalidDataException(ErrorMessage.INVALID_VALUE.getMessageText());
        }
    }

    public Price applyDiscount(DiscountValue discountValue) {
        double result = switch (discountValue.getVoucherType())  {
            case FIXED_AMOUNT -> value - discountValue.getValue();
            case PERCENT_DISCOUNT -> value * (1 - discountValue.getValue()/100);
        };
        return new Price(result);
    }

    public double getValue() {
        return value;
    }
}
