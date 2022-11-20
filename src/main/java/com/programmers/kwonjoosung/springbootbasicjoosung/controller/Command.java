package com.programmers.kwonjoosung.springbootbasicjoosung.controller;

import com.programmers.kwonjoosung.springbootbasicjoosung.exception.WrongCommandException;

import java.util.Objects;
import java.util.stream.Stream;

public enum Command {

    CREATE("create"),
    READ("read"),
    UPDATE("update"),
    DELETE("delete"),
    LIST("list"),
    FIND("find");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command of(String input) {
        return Stream.of(Command.values())
                .filter(value -> Objects.equals(value.command, input.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new WrongCommandException(input));
    }
}
