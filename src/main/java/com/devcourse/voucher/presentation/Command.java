package com.devcourse.voucher.presentation;

public enum Command {
    CREATE,
    LIST,
    EXIT,
    ;

    private static final String NOT_SUPPORT_COMMAND = "[Error] Your Input Is Not Support. Command : ";

    public static Command from(String input) {
        try {
            return Enum.valueOf(Command.class, input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(NOT_SUPPORT_COMMAND + input);
        }
    }

    public boolean isCreation() {
        return this == CREATE;
    }
}
