package org.devcourse.voucher.controller.console;

public enum Command {
    CREATE,
    LIST,
    EXIT;

    public static Command find(String commandName) {
        return valueOf(commandName.toUpperCase());
    }
}
