package org.prgrms.kdt.io;

import java.util.Arrays;

public enum CommandType {
    EXIT ("exit"),
    CREATE ("create"),
    LIST ("list"),
    BLACK_LIST("blacklist"),
    UNKNOWN("알수없음");

    private final String command;

    CommandType(String command){
        this.command = command;
    }

    public static CommandType getCommandType(String command){
        return Arrays.stream(values())
                .filter(commandType -> commandType.command.equals(command))
                .findAny()
                .orElse(UNKNOWN);
    }

}
