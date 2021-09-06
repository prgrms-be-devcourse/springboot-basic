package org.prgrms.kdt.io;

import java.util.Arrays;

public enum CommandType {
    EXIT ("exit"),
    CREATE ("create"),
    LIST ("list"),
    BLACK_LIST("blacklist");

    private final String command;

    CommandType(String command){
        this.command = command;
    }

    public static CommandType getCommandType(String command){
        return Arrays.stream(values())
                .filter(commandType -> commandType.command.equals(command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 값 입력: " + command));
    }

}
