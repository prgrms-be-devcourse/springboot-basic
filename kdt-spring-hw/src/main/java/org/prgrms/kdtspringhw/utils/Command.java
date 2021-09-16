package org.prgrms.kdtspringhw.utils;

import java.util.Arrays;
//https://pjh3749.tistory.com/279
//https://limkydev.tistory.com/66

public enum Command {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACK_LIST("black"),
    ELSE("");
    private final String command;

    Command(String command){
        this.command = command;
    }

    public static Command getCommandType(String command){
        return Arrays.stream(values())//values는 배열을 보낸다.
                .filter(commandType -> commandType.command.equals(command))
                .findAny()
                .orElse(ELSE);
    }
}
