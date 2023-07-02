package org.weekly.weekly.ui.exception;

import org.weekly.weekly.util.ExceptionMsg;

import java.util.Arrays;

public class InputValidator {
    private static final int VOUCHER_INPUT_SIZE = 2;
    public static void isEmpty(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            throw new RuntimeException(ExceptionMsg.EMPTY.getMsg());
        }
    }

    public static void notVoucherInputSize(String[] userInputs) {
        if (userInputs.length != VOUCHER_INPUT_SIZE) {
            throw new InputException(ExceptionMsg.NOT_SAME_PARAM_SIZE);
        }
    }
    
    public static void notVoucherInfoFormat(String[] userInputs) {
        if (Arrays.stream(userInputs)
                .anyMatch(input -> isDigit(input.trim()))) {
            throw new InputException(ExceptionMsg.NOT_INPUT_FORMAT);
        }
    }

    private static boolean isDigit(String userInput) {
        return userInput.chars().anyMatch(value -> !Character.isDigit(value));
    }
}
