package org.prgrms.kdtspringdemo.voucher.constant;

import java.util.Arrays;

public enum CommandType {
    NONE,
    EXIT,
    CREATE,
    LIST;

    private static final String CANT_FIND_COMMAND_TYPE = "알맞는 명령이 없습니다.";

    public static CommandType findCommandType(String userCommand) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.name().equals(userCommand))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(CANT_FIND_COMMAND_TYPE));
    }
}
