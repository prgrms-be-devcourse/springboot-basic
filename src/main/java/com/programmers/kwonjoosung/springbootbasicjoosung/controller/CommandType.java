package com.programmers.kwonjoosung.springbootbasicjoosung.controller;

import com.programmers.kwonjoosung.springbootbasicjoosung.exception.WrongCommandException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandType {

    EXIT("Type exit to exit the program.", "exit"),
    CREATE("Type create to create a new voucher.", "create"),
    LIST("Type list to list all vouchers.", "list"),
    DELETE("delete your voucher.", "delete"),
    UPDATE("update your voucher.", "update"),
    SELECT("Type show your voucher.", "select"),
    BLACKLIST("Type blacklist to list all customer-black-list.", "blacklist"),
    HELP("Type help to show all command.", "help"),
    WALLET("Type wallet to show customer wallet.", "wallet"),
    ASSIGN("Type assign to assign voucher to customer", "assign");

    private final String explanation;
    private final String command;

    CommandType(String explanation, String command) {
        this.explanation = explanation;
        this.command = command;
    }

    public static CommandType getCommand(String input) {
        return Stream.of(CommandType.values())
                .filter(commandType -> commandType.command.equals(input))
                .findFirst()
                .orElseThrow(() -> new WrongCommandException(input));
    }

    public static List<String> getAllExplanation() {
        return Stream.of(CommandType.values())
                .map(commandType -> commandType.explanation)
                .collect(Collectors.toList());
    }
}

