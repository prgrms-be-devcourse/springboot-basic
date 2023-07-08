package org.weekly.weekly.ui.exception;

import org.weekly.weekly.util.ExceptionMsg;

import java.util.Arrays;

public class ReadException {

    public static void isEmpty(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            throw new RuntimeException(ExceptionMsg.EMPTY.getMsg());
        }
    }

    public static void notVoucherInputSize(String[] userInputs) {
        if (userInputs.length != 2) {
            throw new RuntimeException(ExceptionMsg.NOT_SAME_PARAM_SIZE.getMsg());
        }
    }
    
    public static void notVoucherInputFormat(String[] userInputs) {
        if (Arrays.stream(userInputs)
                .anyMatch(input -> isDigit(input.trim()))) {
            throw new RuntimeException(ExceptionMsg.NOT_INPUT_FORMAT.getMsg());
        }
    }

    private static boolean isDigit(String userInput) {
        return userInput.chars().anyMatch(value -> !Character.isDigit(value));
    }
}
