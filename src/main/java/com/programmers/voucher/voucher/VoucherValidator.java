package com.programmers.voucher.voucher;

import org.springframework.util.StringUtils;

import static com.programmers.message.ErrorMessage.VOUCHER_INPUT_ERROR_MESSAGE;
import static com.programmers.voucher.voucher.VoucherType.FixedAmount;
import static com.programmers.voucher.voucher.VoucherType.PercentDiscount;

public class VoucherValidator {
    public static final int MAX_DISCOUNT_AMOUNT = 200000;
    public static final int MIN_DISCOUNT_AMOUNT = 1000;

    public static final int MAX_DISCOUNT_PERCENTAGE = 100;
    public static final int MIN_DISCOUNT_PERCENTAGE = 1;

    public static final String numberRegEx = "[+-]?\\d+";

    public static Voucher getValidateVoucher(String type, String value) {
        VoucherType validateVoucherType = VoucherType.getValidateVoucherType(type);
        long validateValue = ValidateValue(validateVoucherType, value);

        return VoucherFactory.createVoucher(validateVoucherType, validateValue);
    }

    public static long ValidateValue(VoucherType type, String value) {
        if (isNumeric(value) && isProperValue(type, value)) {
            return Long.parseLong(value);
        }

        throw new RuntimeException(VOUCHER_INPUT_ERROR_MESSAGE.getMessage());
    }

    public static boolean isNumeric(String value) {
        if (!StringUtils.hasText(value)) {
            throw new RuntimeException(VOUCHER_INPUT_ERROR_MESSAGE.getMessage());
        }

        value = value.replaceAll(numberRegEx, "");

        if (!value.equals("")) {
            throw new RuntimeException(VOUCHER_INPUT_ERROR_MESSAGE.getMessage());
        }

        return true;
    }

    public static boolean isProperValue(VoucherType type, String inputValue) {
        long value = Long.parseLong(inputValue);

        if (type.equals(PercentDiscount)) {
            return MIN_DISCOUNT_PERCENTAGE <= value && value <= MAX_DISCOUNT_PERCENTAGE;
        }

        if (type.equals(FixedAmount)) {
            return MIN_DISCOUNT_AMOUNT <= value && value <= MAX_DISCOUNT_AMOUNT;
        }

        throw new RuntimeException(VOUCHER_INPUT_ERROR_MESSAGE.getMessage());
    }

}
