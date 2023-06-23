package org.weekly.weekly.ui.reader;

import org.weekly.weekly.util.ExceptionMsg;

public class ReadException {

    public static void isEmpty(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            throw new RuntimeException(ExceptionMsg.EMPTY.getMsg());
        }
    }

    public static void notInputFormat(String userInput) {
        if (containsIntOrBlank(userInput)) {
            throw new RuntimeException(ExceptionMsg.NOT_INPUT_FORMAT.getMsg());
        }
    }

    private static boolean containsIntOrBlank(String userInput) {
        return userInput.chars().anyMatch(value -> value==' ' || Character.isDigit(value));
    }
}
