package com.programmers.vouchermanagement.utils;

import java.util.Arrays;

public enum Command {

    CREATE,
    LIST,
    EXIT;

    public static Command getCommandByName(String name) {
        return Arrays.stream(Command.values())
                .filter(command -> command.getLowerCaseName().equals(name))
                .findAny().orElseThrow(CommandNotFoundException::new);
    }

    public String getLowerCaseName() {
        return this.name().toLowerCase();
    }
}
