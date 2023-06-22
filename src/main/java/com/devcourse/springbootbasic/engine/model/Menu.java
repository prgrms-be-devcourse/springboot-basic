package com.devcourse.springbootbasic.engine.model;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;

import java.util.Arrays;

public enum Menu {
    EXIT("0", "exit"),
    CREATE("1", "create"),
    LIST("2", "list");

    private final String menuOrdinal;
    private final String menuCommand;

    Menu(String menuOrdinal, String menuCommand) {
        this.menuOrdinal = menuOrdinal;
        this.menuCommand = menuCommand;
    }

    public static Menu getMenu(String menuString) {
        return Arrays.stream(Menu.values())
                .filter(m -> m.menuCommand.equals(menuString) || m.menuOrdinal.equals(menuString))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(InvalidDataException.INVALID_MENU));
    }
}
