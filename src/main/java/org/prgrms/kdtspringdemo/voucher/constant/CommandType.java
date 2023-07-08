package org.prgrms.kdtspringdemo.voucher.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum CommandType {
    NONE,
    EXIT,
    CREATE,
    LIST;

    private static final Logger logger = LoggerFactory.getLogger(CommandType.class);
    private static final String CANT_FIND_COMMAND_TYPE = "알맞는 명령이 없습니다.";

    public boolean isRunning() {
        return this != EXIT;
    }

    public static CommandType findCommandType(String userCommand) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.name().equals(userCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("원인 : {} -> 에러 메시지 : {}", userCommand, CANT_FIND_COMMAND_TYPE);
                    throw new IllegalArgumentException(CANT_FIND_COMMAND_TYPE);
                });
    }
}
