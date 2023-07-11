package org.prgrms.kdtspringdemo.voucher.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum VoucherCommandType {
    CREATE,
    LIST_ALL,
    LIST,
    DELETE;

    private static final Logger logger = LoggerFactory.getLogger(VoucherCommandType.class);
    private static final String CANT_FIND_COMMAND_TYPE = "알맞는 명령이 없습니다. 처음부터 다시하세요.";

    public static VoucherCommandType findCommandType(String userCommand) {
        return Arrays.stream(VoucherCommandType.values())
                .filter(voucherCommandType -> voucherCommandType.name().equals(userCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("원인 : {} -> 에러 메시지 : {}", userCommand, CANT_FIND_COMMAND_TYPE);
                    throw new IllegalArgumentException(CANT_FIND_COMMAND_TYPE);
                });
    }
}
