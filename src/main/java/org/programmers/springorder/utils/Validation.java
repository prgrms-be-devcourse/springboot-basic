package org.programmers.springorder.utils;

import org.programmers.springorder.consts.ErrorMessage;
import org.programmers.springorder.voucher.model.VoucherType;

import java.util.InputMismatchException;

public class Validation {
    public static String validateString(String input) {
        if (input == null || input.isBlank()) {
            throw new InputMismatchException(ErrorMessage.EMPTY_VALUE_MESSAGE);
        }
        return input;
    }


    public static long validateDiscountValue(String input, VoucherType voucherType) throws InputMismatchException, NumberFormatException{
        long longValue = validateLong(input);
        validateDiscountRange(longValue, voucherType);
        return longValue;
    }

    private static long validateLong(String input) {
        return Long.parseLong(input);
    }

    private static void validateDiscountRange(long input, VoucherType voucherType) {
        if (input < voucherType.getMinimumValue() || input > voucherType.getMaximumValue()) {
            throw new InputMismatchException();
        }
    }

}
