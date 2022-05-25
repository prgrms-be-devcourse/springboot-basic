package org.devcourse.voucher.core.menu.model;

import org.devcourse.voucher.core.exception.ErrorType;

import java.util.Arrays;

public enum MainMenuType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String command;

    MainMenuType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static MainMenuType discriminate(String input) {
        return Arrays.stream(values())
                .filter(type -> type.getCommand().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorType.INVALID_COMMAND.message()));
    }
}
