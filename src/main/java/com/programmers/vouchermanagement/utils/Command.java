package com.programmers.vouchermanagement.utils;

import com.programmers.vouchermanagement.exception.CommandNotFoundException;

import java.util.Arrays;

public enum Command {

    CREATE,
    LIST,
    BLACKLIST,
    EXIT;

    public static Command getCommandByName(String name) {
        return Arrays.stream(Command.values())
                .filter(command -> command.getLowerCaseName().equals(name.toLowerCase()))
                .findAny().orElseThrow(CommandNotFoundException::new);
    }

    private String getLowerCaseName() {
        return this.name().toLowerCase();
    }
}
