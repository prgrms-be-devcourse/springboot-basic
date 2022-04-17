package com.programmers.part1.ui;

import com.programmers.part1.exception.CommandTypeMissingException;

import java.util.Arrays;

public enum CommandType {

    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACKLIST("blacklist");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType getCommandType(String command) {
        return Arrays.stream(values())
                .filter(type -> type.command.equals(command))
                .findAny()
                .orElseThrow(() -> new CommandTypeMissingException("올바른 명령이 아닙니다. 다시 입력해주세요."));
    }
}
