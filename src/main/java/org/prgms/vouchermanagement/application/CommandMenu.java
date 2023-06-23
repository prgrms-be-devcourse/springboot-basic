package org.prgms.vouchermanagement.application;

import org.prgms.vouchermanagement.constant.ExceptionMessageConstant;

public enum CommandMenu {

    START("start"),
    EXIT("exit"),
    CREATE_NEW_VOUCHER("create"),
    SHOW_VOUCHER_LIST("list"),
    SHOW_BLACK_LIST("black");

    private final String command;

    CommandMenu(String command) {
        this.command = command;
    }

    public static CommandMenu getCommandMenu(String input) {
        switch (input.toLowerCase()) {
            case "exit":
                return EXIT;
            case "create":
                return CREATE_NEW_VOUCHER;
            case "list":
                return SHOW_VOUCHER_LIST;
            case "black":
                return SHOW_BLACK_LIST;
            default:
                throw new IllegalArgumentException(ExceptionMessageConstant.COMMAND_INPUT_EXCEPTION);
        }
    }
}