package com.example.voucher.utils;

import static com.example.voucher.utils.ExceptionMessage.MESSAGE_ERROR_POSITIVE_CONSTRAINT;
import static com.example.voucher.utils.ExceptionMessage.MESSAGE_ERROR_RANGE_CONSTRAINT;
import static com.example.voucher.utils.ExceptionMessage.MESSAGE_ERROR_NON_ZERO_CONSTRAINT;
import static com.example.voucher.utils.ExceptionMessage.FORMAT_ERROR_GREATER_THAN_CONSTRAINT;

import com.example.voucher.constant.VoucherType;
import com.example.voucher.constant.ModeType;

public class Validator {

    private Validator() {

    }

    public static ModeType validateModeTypeMatch(String inputModeType) {
        return ModeType.getModeType(inputModeType);

    }

    public static VoucherType validateVoucherTypeMatch(Integer inputVoucherType) {
        return VoucherType.getVouchersType(inputVoucherType);

    }

    public static void validatePositive(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_POSITIVE_CONSTRAINT);
        }

    }

    public static void validatePercent(long percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException(MESSAGE_ERROR_RANGE_CONSTRAINT);
        }

    }

    public static void validateNonZero(long value) {
        if (value == 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_NON_ZERO_CONSTRAINT);
        }

    }

    public static void validateGreaterThan(long value, long threshold) {
        if (value <= threshold) {
            throw new IllegalArgumentException(
                String.format("{} {}", FORMAT_ERROR_GREATER_THAN_CONSTRAINT, threshold));
        }

    }

}
