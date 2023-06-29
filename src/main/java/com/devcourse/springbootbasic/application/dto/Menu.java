package com.devcourse.springbootbasic.application.dto;

import com.devcourse.springbootbasic.application.constant.Message;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum Menu {
    EXIT("0", "exit", "to exit the program."),
    CREATE("1", "create", "to create a new voucher."),
    LIST("2", "list", "to list all vouchers.");

    private static final Logger logger = LoggerFactory.getLogger(Menu.class);

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
                .orElseThrow(() -> {
                    logger.error("Menu Error - {} : {}", menuString, Message.INVALID_MENU);
                    return new InvalidDataException(Message.INVALID_MENU.getMessageText());
                });
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
