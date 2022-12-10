package org.prgrms.java.common;

import org.prgrms.java.exception.CommandException;

import java.util.Arrays;

public enum HandlingCommand {
    CREATE,
    FIND,
    LIST,
    BLACKLIST,
    ALLOCATE,
    SHOW,
    DELETE,
    DELETE_ALL;

    public static HandlingCommand get(String command) {
        return Arrays.stream(HandlingCommand.values())
                .filter((item) -> item.toString().equals(command.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new CommandException("Please enter a valid handling command."));
    }
}
