package com.programmers.voucher.voucher;

import java.util.Arrays;

import static com.programmers.voucher.menu.Message.VOUCHER_INPUT_ERROR_MESSAGE;
import static com.programmers.voucher.voucher.VoucherType.*;

public class VoucherValidator {
    public static final int MAX_DISCOUNT_COST = 200000;
    public static final int MIN_DISCOUNT_COST = 1000;

    public static final int MAX_DISCOUNT_PERCENTAGE = 100;
    public static final int MIN_DISCOUNT_PERCENTAGE = 1;

    public static final String numberRegEx = "[+-]?\\d+";

    public static Voucher getValidateVoucher(String type, String value) {
        VoucherType validateVoucherType = getValidateVoucherType(type);
        long validateValue = ValidateValue(validateVoucherType, value);

        return validateVoucherType.createVoucher(validateValue);
    }

    public static long ValidateValue(VoucherType type, String value) {
        if (isNumeric(value) && isProperValue(type, value)) {
            return Long.parseLong(value);
        }

        throw new RuntimeException(VOUCHER_INPUT_ERROR_MESSAGE.getMessage());
    }

    public static boolean isNumeric(String value) {
        value = value.replaceAll(numberRegEx, "");

        if (value.equals("")) {
            return true;
        }

        throw new RuntimeException(VOUCHER_INPUT_ERROR_MESSAGE.getMessage());
    }

    public static boolean isProperValue(VoucherType type, String inputValue) {
        long value = Long.parseLong(inputValue);

        if (type.equals(PercentDiscount)) {
            return MIN_DISCOUNT_PERCENTAGE <= value && value <= MAX_DISCOUNT_PERCENTAGE;
        }

        if (type.equals(FixedAmount)) {
            return MIN_DISCOUNT_COST <= value && value <= MAX_DISCOUNT_COST;
        }

        throw new RuntimeException(VOUCHER_INPUT_ERROR_MESSAGE.getMessage());
    }

    public static VoucherType getValidateVoucherType(String inputType) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.getType().contains(inputType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(VOUCHER_INPUT_ERROR_MESSAGE.getMessage()));
    }
}
