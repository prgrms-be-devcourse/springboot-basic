package com.programmers.kwonjoosung.springbootbasicjoosung.controller;

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
                .orElseThrow(() -> new IllegalArgumentException("올바른 명령어가 아닙니다."));
    }
}
