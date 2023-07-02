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
    
    public static void notNumber(String[] userInputs) {
        try {
            Arrays.stream(userInputs)
                    .peek(Long::parseLong);
        } catch (NumberFormatException exception) {
            throw new InputException(ExceptionMsg.NOT_INPUT_FORMAT);
        }
    }
}
