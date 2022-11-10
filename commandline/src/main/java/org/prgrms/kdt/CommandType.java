package org.prgrms.kdt;

import org.prgrms.kdt.exception.WrongCommand;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandType {
    CREAT("create", "Type 'create' to create a new voucher."),
    LIST("list", "Type 'list' to list all vouchers."),
    EXIT("exit", "Type 'exit' to exit the program.");

    private final String command;
    private final String expression;

    CommandType(String command, String expression) {
        this.command = command;
        this.expression = expression;
    }

    public static CommandType selectType(String input) {
        return Stream.of(values())
                .filter(value -> value.command.equals(input))
                .findFirst()
                .orElseThrow(() -> new WrongCommand());
    }

    public static String getAllCommandExpression() {
        return Stream.of(values())
                .map(cmdType -> cmdType.expression)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}

