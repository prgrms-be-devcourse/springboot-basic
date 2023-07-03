package com.devcourse.springbootbasic.application.dto;

import com.devcourse.springbootbasic.application.constant.ErrorMessage;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;

import java.util.Arrays;

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
                .filter(m -> m.menuCommand.equals(menuString) || m.menuOrdinal.equals(menuString))
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
