package org.prgms.voucher.entity;

import java.util.Arrays;

import org.prgms.voucher.exception.WrongInputMenuException;

public enum MenuType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

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
