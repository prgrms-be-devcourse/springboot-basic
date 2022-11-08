package com.programmers.voucher.voucher;

public class VoucherValidator {
    public static final int MAX_DISCOUNT_COST = 200000;
    public static final int MIN_DISCOUNT_COST = 1000;

    public static final int MAX_DISCOUNT_PERCENTAGE = 100;
    public static final int MIN_DISCOUNT_PERCENTAGE = 1;

    public static final String numberRegEx = "[+-]?\\d+";

    public static boolean isValidateValue(String type, String value) {
        if (!isNumeric(value)) {
            return false;
        }

        return isProperValue(type, value);
    }

    public static boolean isNumeric(String value) {
        value = value.replaceAll(numberRegEx, "");
        return value.equals("");
    }

    public static boolean isProperValue(String type, String s) {
        int value = Integer.parseInt(s);
        if (type.equals(VoucherType.PercentDiscount.getType())) {
            return MIN_DISCOUNT_PERCENTAGE <= value && value <= MAX_DISCOUNT_PERCENTAGE;
        }

        if (type.equals(VoucherType.FixedAmount.getType())) {
            return MIN_DISCOUNT_COST <= value && value <= MAX_DISCOUNT_COST;
        }

        return false;
    }
}
