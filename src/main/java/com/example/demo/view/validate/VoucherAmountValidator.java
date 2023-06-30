package com.example.demo.view.validate;

import java.util.regex.Pattern;

public class VoucherAmountValidator {

    private static final String ERROR_MESSAGE_NOT_INTEGER_VALUE = "[ERROR] 1이상의 숫자를 입력해주세요.";
    private static final String ERROR_MESSAGE_NOT_PERCENT_VALUE = "[ERROR] 1이상 ~ 100 이하의 숫자를 입력해주세요.";

    private static final Pattern IS_NUMERIC_REGEX = Pattern.compile("^[1-9]\\d*$");
    private static final Pattern IS_PERCENT_REGEX = Pattern.compile("^([1-9]|[1-9][0-9]|100)$");


    public void validateFixedAmount(String input) {
        isPositiveNumber(input);
    }

    public void validatePercentAmount(String input) {
        isPositiveNumber(input);
        isPercentageNumber(input);
    }

    private void isPositiveNumber(String input) {
        if (!IS_NUMERIC_REGEX.matcher(input).matches()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NOT_INTEGER_VALUE);
        }
    }

    private void isPercentageNumber(String input) {
        if (!IS_PERCENT_REGEX.matcher(input).matches()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NOT_PERCENT_VALUE);
        }
    }
}
