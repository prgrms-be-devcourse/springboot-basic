package org.devcourse.voucher.controller.console;

public enum Command {
    CREATE,
    LIST,
    EXIT,
    RETRY;

    public static Command find(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return RETRY;
        }
    }
}
