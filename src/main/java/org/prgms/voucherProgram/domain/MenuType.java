package org.prgms.voucherProgram.domain;

import java.util.Arrays;

public enum MenuType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACKLIST("blacklist");

    private static final String ERROR_WRONG_INPUT_MENU_MESSAGE = "[ERROR] 올바른 메뉴 입력이 아닙니다.";

    private final String command;

    MenuType(String command) {
        this.command = command;
    }

    public static MenuType from(String command) {
        return Arrays.stream(MenuType.values())
            .filter(type -> type.command.equals(command))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(ERROR_WRONG_INPUT_MENU_MESSAGE));
    }
}
