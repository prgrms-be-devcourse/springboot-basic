package org.devcourse.voucher.controller.console;

public enum Command {
    CREATE,
    LIST,
    EXIT;

    public static Command find(String commandName) {
        try {
            return valueOf(commandName.toUpperCase());
        } catch (NullPointerException e) {
            throw new RuntimeException("지원하지 않는 명령 입니다");
        }
    }
}
