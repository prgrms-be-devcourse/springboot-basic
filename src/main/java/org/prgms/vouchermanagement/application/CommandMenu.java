package org.prgms.vouchermanagement.application;

import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;

public enum CommandMenu {

    EXIT("exit"),
    CREATE_NEW_VOUCHER("create"),
    SHOW_VOUCHER_LIST("list"),
    SHOW_BLACK_LIST("black");

    private final String command;

    CommandMenu(String command) {
        this.command = command;
    }

    public static CommandMenu getCommandMenu(String input) {
        return switch (input.toLowerCase()) {
            case "exit" -> EXIT;
            case "create" -> CREATE_NEW_VOUCHER;
            case "list" -> SHOW_VOUCHER_LIST;
            case "black" -> SHOW_BLACK_LIST;
            default -> throw new IllegalArgumentException(ExceptionMessageConstant.COMMAND_INPUT_EXCEPTION);
        };
    }
}
