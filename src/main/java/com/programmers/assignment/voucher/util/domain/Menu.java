package com.programmers.assignment.voucher.util.domain;

import java.util.Arrays;

public enum Menu {
    EXIT("EXIT"),
    CREATE("CREATE"),
    LIST("LIST");

    private final String commandInput;

    Menu(String command) {
        this.commandInput = command;
    }

    @Override
    public String toString() {
        return commandInput;
    }

    public static String chooseMenu(String commandInput) {
        return getMenu(commandInput).toString();
    }

    private static Menu getMenu(String command) {
        return Arrays.stream(values())
                .filter(o -> o.commandInput.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(command + "는 없는 메뉴 입니다."));
    }
}
