package com.devcourse.springbootbasic.application.global.model;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;

import java.util.Arrays;
import java.util.Objects;

public enum CommandMenu {
    EXIT("Input 0 or EXIT to exit program"),
    REGISTER_CUSTOMER("Input 1 or REGISTER_CUSTOMER to register customer"),
    UNREGISTER_CUSTOMER("Input 2 or UNREGISTER_CUSTOMER to unregister customer"),
    LIST_ALL_CUSTOMERS("Input 3 or LIST_ALL_CUSTOMERS to list all customers"),
    REGISTER_VOUCHER_TO_CUSTOMER("Input 4 or REGISTER_VOUCHER_TO_CUSTOMER to register voucher to customer"),
    UNREGISTER_VOUCHER_FROM_CUSTOMER("Input 5 or UNREGISTER_VOUCHER_OF_CUSTOMER to unregister voucher of customer"),
    LIST_VOUCHERS_OF_CUSTOMER("Input 6 or LIST_VOUCHERS_OF_CUSTOMERS to list vouchers of customer"),
    LIST_BLACK_CUSTOMERS("Input 7 or LIST_BLACK_CUSTOMERS to list all black customers");

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
