package org.prgrms.kdtspringdemo.voucher.constant;

import java.util.Arrays;

public enum CommandType {
    NONE,
    EXIT,
    CREATE,
    LIST;

    public static CommandType findCommandType(String userCommand) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.name().equals(userCommand))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("입력을 잘못하였습니다."));
    }
}
