package com.example.demo.view.validate;

import com.example.demo.util.VoucherType;
import java.util.regex.Pattern;

public final class VoucherAmountValidator {

    private static final String ERROR_MESSAGE_NOT_INTEGER_VALUE = "[ERROR] 1이상의 숫자를 입력해주세요.";
    private static final String ERROR_MESSAGE_NOT_PERCENT_VALUE = "[ERROR] 1이상 ~ 100 이하의 숫자를 입력해주세요.";

    private static final Pattern IS_NUMERIC_REGEX = Pattern.compile("^[1-9]\\d*$");
    private static final Pattern IS_PERCENT_REGEX = Pattern.compile("^([1-9]|[1-9][0-9]|100)$");


    private VoucherAmountValidator() {
    }

    private static void validatePositiveNumber(String input) {
        if (!IS_NUMERIC_REGEX.matcher(input).matches()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NOT_INTEGER_VALUE);
        }
    }

    private static void validatePercentageNumber(String input) {
        if (!IS_PERCENT_REGEX.matcher(input).matches()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NOT_PERCENT_VALUE);
        }
    }

    public static void validateAmount(VoucherType voucherType, String input) {
        switch (voucherType) {
            case FIX -> VoucherAmountValidator.validatePositiveNumber(input);
            case PERCENT -> {
                VoucherAmountValidator.validatePositiveNumber(input);
                VoucherAmountValidator.validatePercentageNumber(input);
            }
            default -> throw new IllegalArgumentException(String.format("입력하신 %s는 유효한 바우처 타입이 아닙니다.", voucherType.toString()));
        }
    }
}
