package org.prgrms.java.common;

import java.util.Arrays;

public enum HandlingCommand {
    CREATE,
    FIND,
    LIST,
    BLACKLIST,
    ALLOCATE,
    SHOW,
    DELETE,
    DELETE_ALL,
    GO_BACK;

    public static HandlingCommand get(String command) {
        return Arrays.stream(HandlingCommand.values())
                .filter((item) -> item.toString().equals(command.toUpperCase()))
                .findAny()
                .orElse(GO_BACK);
    }
}
