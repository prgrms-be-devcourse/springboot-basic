package org.prgms.vouchermanagement.application;

import org.prgms.vouchermanagement.constant.ExceptionMessageConstant;

import java.util.InputMismatchException;

public enum CommandMenu {

    START("start"),
    EXIT("exit"),
    CREATE_NEW_VOUCHER("create"),
    SHOW_VOUCHER_LIST("list");

    private final String command;

    CommandMenu(String command) {
        this.command = command;
    }

    public static CommandMenu getCommandMenu(String input) {
        return switch (input.toLowerCase()) {
            case "exit" -> EXIT;
            case "create" -> CREATE_NEW_VOUCHER;
            case "list" -> SHOW_VOUCHER_LIST;
            default -> throw new InputMismatchException(ExceptionMessageConstant.COMMAND_INPUT_EXCEPTION);
        };
    }
}