package org.programmers.springorder.utils;

public class Validation {
    public static String validateString(String input) {
        if(input == null || input.trim().isEmpty()) {
            throw new VoucherException("값을 입력해주세요.");
        }
        return input;
    }

    public static int validateInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new VoucherException("유효하지 않은 값입니다. 다시 입력해주세요.");
        }
    }

    public static long validateLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new VoucherException("유효하지 않은 값입니다. 다시 입력해주세요.");
        }
    }

}
