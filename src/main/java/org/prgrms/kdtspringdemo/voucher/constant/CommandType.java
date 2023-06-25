package org.prgrms.kdtspringdemo.voucher.constant;

import java.util.Arrays;

public enum CommandType {
    EXIT,
    CREATE,
    LIST;

    public static CommandType findCommandType(String userCommand) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.name().equals(userCommand))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("입력을 잘못하였습니다."));
    }
}
