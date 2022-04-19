package org.programmers.kdt.weekly.io;

public enum InputErrorType {
    BLACK_LIST_EMPTY("There are no saved blacklist."),
    COMMAND("This command does not exist."),
    INVALID("Invalid input."),
    VOUCHER_EMPTY("There are no saved vouchers.");

    private final String message;

    InputErrorType(String message) {
        this.message = message;
    }

    public static String getMessage(InputErrorType inputErrorType) {
        return inputErrorType.message;
    }
}