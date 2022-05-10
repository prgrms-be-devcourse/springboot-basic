package org.devcourse.voucher.menu.model;

import java.util.Arrays;

public enum MainMenuType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    NONE("");

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
                .orElse(NONE);
    }
}
