package org.weekly.weekly.ui.exception;

import org.weekly.weekly.util.ExceptionMsg;
import org.weekly.weekly.voucher.exception.VoucherException;

import java.util.Arrays;

public class InputValidator {

    private InputValidator() {
        throw new InputException(ExceptionMsg.UTIL_CLASS);
    }

    private static final int VOUCHER_INPUT_SIZE = 2;
    public static void isEmpty(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            throw new InputException(ExceptionMsg.EMPTY);
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
