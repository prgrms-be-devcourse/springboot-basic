package com.devcourse.springbootbasic.application.dto;

import com.devcourse.springbootbasic.application.constant.ErrorMessage;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;

import java.util.Objects;

public class DiscountValue {

    private final double discountValue;

    public DiscountValue(VoucherType voucherType, String value) {
        this.discountValue = Double.parseDouble(value);
        validatePositive();
        validatePercent(voucherType);
    }

    private void validatePositive() {
        if (discountValue < 0) {
            throw new InvalidDataException(ErrorMessage.INVALID_DISCOUNT_VALUE.getMessageText());
        }
    }

    private void validatePercent(VoucherType voucherType) {
        if (Objects.equals(voucherType, VoucherType.PERCENT_DISCOUNT) && discountValue > 100) {
            throw new InvalidDataException(ErrorMessage.INVALID_DISCOUNT_VALUE.getMessageText());
        }
    }

    public double getValue() {
        return discountValue;
    }

}
