package com.programmers.kwonjoosung.springbootbasicjoosung.controller;

import com.programmers.kwonjoosung.springbootbasicjoosung.exception.WrongCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.stream.Stream;

public enum CommandType {
    EXIT("Type exit to exit the program.", "exit"),
    CREATE("Type create to create a new voucher.", "create"),
    LIST("Type list to list all vouchers.", "list"),
    BLACKLIST("Type blacklist to list all customer-black-list.", "blacklist"),
    HELP("Type help to show all command.", "help");
    private static final Logger logger = LoggerFactory.getLogger(CommandType.class);
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
                .orElseThrow(() -> {
                    logger.error("Wrong CommandType = {}", input);
                    return new WrongCommandException(input);
                });
    }

    public String getExplanation() {
        return explanation;
    }
}

