package com.devcourse.springbootbasic.application.voucher.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.util.Objects;

public record DiscountValue(
        double value
) {
    
    public static DiscountValue from(VoucherType voucherType, String valueString) {
        var parsedValue = parseDiscountValue(valueString);
        validatePositive(parsedValue);
        validatePercent(voucherType, parsedValue);
        return new DiscountValue(parsedValue);
    }

    public static DiscountValue from(VoucherType voucherType, double valueDouble) {
        validatePositive(valueDouble);
        validatePercent(voucherType, valueDouble);
        return new DiscountValue(valueDouble);
    }

    private static double parseDiscountValue(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            throw new InvalidDataException(ErrorMessage.INVALID_DISCOUNT_VALUE.getMessageText(), e.getCause());
        }
    }

    private static void validatePositive(double parsedValue) {
        if (parsedValue < 0) {
            throw new InvalidDataException(ErrorMessage.INVALID_DISCOUNT_VALUE.getMessageText());
        }
    }

    private static void validatePercent(VoucherType voucherType, double parsedValue) {
        if (Objects.equals(voucherType, VoucherType.PERCENT_DISCOUNT) && parsedValue > 100) {
            throw new InvalidDataException(ErrorMessage.INVALID_DISCOUNT_VALUE.getMessageText());
        }
    }

}
