package org.prgrms.kdtspringdemo.view.constant;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherCommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum MainCommandType {
    EXIT,
    VOUCHER,
    CUSTOMER;

    private static final Logger logger = LoggerFactory.getLogger(VoucherCommandType.class);
    private static final String CANT_FIND_COMMAND_TYPE = "알맞는 명령이 없습니다.";

    public boolean isRunning() {
        return this != EXIT;
    }

    public static MainCommandType findCommandType(String userCommand) {
        return Arrays.stream(MainCommandType.values())
                .filter(mainCommandType -> mainCommandType.name().equals(userCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("원인 : {} -> 에러 메시지 : {}", userCommand, CANT_FIND_COMMAND_TYPE);
                    throw new IllegalArgumentException(CANT_FIND_COMMAND_TYPE);
                });
    }
}
