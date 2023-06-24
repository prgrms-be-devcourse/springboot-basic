package org.prgrms.kdtspringdemo.Voucher.constant;

import java.util.Arrays;

public enum CommandType {
    EXIT,
    CREATE,
    LIST;

    public static CommandType findCommandType(String userInputCommand) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.name().equals(userInputCommand))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("입력을 잘못하였습니다."));
    }
}
