package org.prgms.voucherProgram.domain.menu;

import java.util.Arrays;

public enum VoucherMenuType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACKLIST("blacklist");

    private static final String ERROR_WRONG_INPUT_MENU_MESSAGE = "[ERROR] 올바른 메뉴 입력이 아닙니다.";

    private final String command;

    VoucherMenuType(String command) {
        this.command = command;
    }

    public static VoucherMenuType from(String command) {
        return Arrays.stream(VoucherMenuType.values())
            .filter(type -> type.command.equalsIgnoreCase(command))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(ERROR_WRONG_INPUT_MENU_MESSAGE));
    }
}
