package org.programmers.springboot.basic.util;

import org.programmers.springboot.basic.util.exception.CommandNotFoundException;

import java.util.Arrays;

public enum CommandType {

    CREATE,
    LIST,
    BLACKLIST,
    EXIT;

    public static CommandType valueOfCommand(String command) {

        return Arrays.stream(values())
                .filter(value -> value.getLowerCase().equals(command))
                .findAny()
                .orElseThrow(CommandNotFoundException::new);
    }

    public String getLowerCase() {
        return this.name().toLowerCase();
    }
}
