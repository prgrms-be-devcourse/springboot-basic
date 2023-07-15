package org.weekly.weekly.ui.exception;

import org.weekly.weekly.util.ExceptionCode;

import java.util.Arrays;

public class InputValidator {

    private InputValidator() {
        throw new InputException(ExceptionCode.UTIL_CLASS);
    }

    private static final int VOUCHER_INPUT_SIZE = 2;

    public static void isEmpty(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            throw new InputException(ExceptionCode.EMPTY);
        }
    }

    public static void notVoucherInputSize(String[] userInputs) {
        if (userInputs.length != VOUCHER_INPUT_SIZE) {
            throw new InputException(ExceptionCode.NOT_SAME_PARAM_SIZE);
        }
    }

    public static void notNumber(String[] userInputs) {
        try {
            Arrays.stream(userInputs)
                    .forEach(Long::parseLong);
        } catch (NumberFormatException exception) {
            throw new InputException(ExceptionCode.NOT_INPUT_FORMAT);
        }
    }
}
