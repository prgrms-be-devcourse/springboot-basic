package com.tengerine.voucher_system.application.global.model;

import com.tengerine.voucher_system.application.global.exception.ErrorMessage;
import com.tengerine.voucher_system.application.global.exception.InvalidDataException;

import java.util.Arrays;
import java.util.Objects;

public enum CommandMenu {
    EXIT("Input 0 or EXIT to exit program"),
    CUSTOMER("Input 1 or CUSTOMER to do customer task"),
    VOUCHER("Input 2 or VOUCHER to do voucher task"),
    WALLET("Input 3 or WALLET to do wallet task");

    private final String menuPrompt;

    CommandMenu(String menuPrompt) {
        this.menuPrompt = menuPrompt;
    }

    public static CommandMenu getCommandMenu(String menuString) {
        return Arrays.stream(CommandMenu.values())
                .filter(commandMenu -> isMatched(menuString, commandMenu))
                .findAny()
                .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_MENU.getMessageText()));
    }

    private static boolean isMatched(String menuString, CommandMenu commandMenu) {
        boolean isMatchedName = Objects.equals(menuString, commandMenu.name());
        boolean isMatchedOrdinal = Objects.equals(menuString, String.valueOf(commandMenu.ordinal()));
        return isMatchedName || isMatchedOrdinal;
    }

    public String getMenuPrompt() {
        return menuPrompt;
    }

}
