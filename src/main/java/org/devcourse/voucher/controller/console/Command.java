package org.devcourse.voucher.controller.console;

public enum Command {
    CREATE,
    LIST,
    EXIT;

    public static Command find(String name) {
        return valueOf(name.toUpperCase());
    }
}
