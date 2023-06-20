package com.devcourse.springbootbasic.engine.model;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;

import java.util.Arrays;

public enum Menu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String menuCommand;

    Menu(String menuCommand) {
        this.menuCommand = menuCommand;
    }

    public static Menu getMenu(String menuString) {
        String lowerMenuString = menuString.toLowerCase();
        return Arrays.stream(Menu.values())
                .filter(m -> m.menuCommand.equals(lowerMenuString))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(InvalidDataException.INVALID_MENU));
    }
}
