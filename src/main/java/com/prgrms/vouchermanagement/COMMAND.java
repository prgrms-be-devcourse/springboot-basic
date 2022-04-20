package com.prgrms.vouchermanagement;

import java.util.Arrays;

public enum COMMAND {
    CREATE_COMMAND("create"), LIST_COMMAND("list"), EXIT_COMMAND("exit"), BLACK_LIST_COMMAND("blacklist");

    private final String commandStr;

    COMMAND(String commandStr) {
        this.commandStr = commandStr;
    }

    public static COMMAND getCommand(String commandStr) throws IllegalArgumentException {
        return Arrays.stream(COMMAND.values())
                .filter(command -> command.commandStr.equals(commandStr))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("commandStr과 매치되는 COMMAND가 없습니다."));
    }

    public static boolean contain(String commandStr) {
        return Arrays.stream(COMMAND.values())
                .anyMatch(command -> command.commandStr.equals(commandStr));
    }
}
