package com.devcourse.springbootbasic.application.voucher.vo;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.util.Objects;

public class DiscountValue {
    private final double value;

    public DiscountValue(VoucherType voucherType, String valueString) {
        double parsedValue = parseDiscountValue(valueString);
        validatePositive(parsedValue);
        validatePercent(voucherType, parsedValue);
        this.value = parsedValue;
    }

    private double parseDiscountValue(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            throw new InvalidDataException(ErrorMessage.INVALID_DISCOUNT_VALUE.getMessageText(), e.getCause());
        }
    }

    private void validatePositive(double parsedValue) {
        if (parsedValue < 0) {
            throw new InvalidDataException(ErrorMessage.INVALID_DISCOUNT_VALUE.getMessageText());
        }
    }

    private void validatePercent(VoucherType voucherType, double parsedValue) {
        if (Objects.equals(voucherType, VoucherType.PERCENT_DISCOUNT) && parsedValue > 100) {
            throw new InvalidDataException(ErrorMessage.INVALID_DISCOUNT_VALUE.getMessageText());
        }
    }

    public double getValue() {
        return value;
    }

}
