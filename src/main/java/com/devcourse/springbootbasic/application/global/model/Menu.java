package com.devcourse.springbootbasic.application.global.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.util.Arrays;
import java.util.Objects;

public enum Menu {
    EXIT("0", "exit", "to exit the program."),
    CREATE("1", "create", "to create a new voucher."),
    LIST("2", "list", "to list all vouchers.");

    private final String menuOrdinal;
    private final String menuCommand;
    private final String menuPrompt;

    Menu(String menuOrdinal, String menuCommand, String menuPrompt) {
        this.menuOrdinal = menuOrdinal;
        this.menuCommand = menuCommand;
        this.menuPrompt = menuPrompt;
    }

    public static Menu getMenu(String menuString) {
        return Arrays.stream(Menu.values())
                .filter(menu -> Objects.equals(menu.menuCommand, menuString) || Objects.equals(menu.menuOrdinal, menuString))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_MENU.getMessageText()));
    }

    public String getMenuOrdinal() {
        return menuOrdinal;
    }

    public String getMenuCommand() {
        return menuCommand;
    }

    public String getMenuPrompt() {
        return menuPrompt;
    }
}
