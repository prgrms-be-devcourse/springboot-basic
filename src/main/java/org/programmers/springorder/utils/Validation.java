package org.programmers.springorder.utils;

import org.programmers.springorder.consts.ErrorMessage;

public class Validation {
    public static String validateString(String input) {
        if(input == null || input.trim().isEmpty()) {
            throw new VoucherException(ErrorMessage.EMPTY_VALUE_MESSAGE);
        }
        return input;
    }

    public static int validateInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new VoucherException(ErrorMessage.INVALID_VALUE_MESSAGE);
        }
    }

    public static long validateLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new VoucherException(ErrorMessage.INVALID_VALUE_MESSAGE);
        }
    }

}
