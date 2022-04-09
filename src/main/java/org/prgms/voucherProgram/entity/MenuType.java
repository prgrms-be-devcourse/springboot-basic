package org.prgms.voucherProgram.entity;

import java.util.Arrays;

import org.prgms.voucherProgram.exception.WrongInputMenuException;

public enum MenuType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACKLIST("blacklist");

    private final String command;

    MenuType(String command) {
        this.command = command;
    }

    public static MenuType of(String command) throws WrongInputMenuException {
        return Arrays.stream(MenuType.values())
            .filter(type -> type.command.equals(command))
            .findFirst()
            .orElseThrow(WrongInputMenuException::new);
    }
}
