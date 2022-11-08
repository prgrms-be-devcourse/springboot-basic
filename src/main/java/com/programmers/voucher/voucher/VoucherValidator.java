package com.programmers.voucher.voucher;

import static com.programmers.voucher.menu.Message.INPUT_ERROR_MESSAGE;
import static com.programmers.voucher.voucher.VoucherType.FixedAmount;
import static com.programmers.voucher.voucher.VoucherType.PercentDiscount;

public class VoucherValidator {
    public static final int MAX_DISCOUNT_COST = 200000;
    public static final int MIN_DISCOUNT_COST = 1000;

    public static final int MAX_DISCOUNT_PERCENTAGE = 100;
    public static final int MIN_DISCOUNT_PERCENTAGE = 1;

    public static final String numberRegEx = "[+-]?\\d+";

    public static void ValidateValue(VoucherType type, String value) {
        isNumeric(value);
        isProperValue(type, value);
    }

    public static boolean isNumeric(String value) {
        value = value.replaceAll(numberRegEx, "");

        if(value.equals("")){
            return true;
        }

        throw new RuntimeException(INPUT_ERROR_MESSAGE.getMessage());
    }

    public static boolean isProperValue(VoucherType type, String inputValue) {
        long value = Long.parseLong(inputValue);

        if (type.equals(PercentDiscount)) {
            return MIN_DISCOUNT_PERCENTAGE <= value && value <= MAX_DISCOUNT_PERCENTAGE;
        }

        if (type.equals(FixedAmount)) {
            return MIN_DISCOUNT_COST <= value && value <= MAX_DISCOUNT_COST;
        }

        throw new RuntimeException(INPUT_ERROR_MESSAGE.getMessage());
    }
}
