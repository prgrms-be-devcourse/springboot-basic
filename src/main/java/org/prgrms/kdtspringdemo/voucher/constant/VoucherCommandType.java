package org.prgrms.kdtspringdemo.voucher.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.prgrms.kdtspringdemo.voucher.exception.VoucherExceptionMessage.NOT_FOUND_VOUCHER_COMMAND_TYPE;

public enum VoucherCommandType {
    CREATE,
    LIST_ALL,
    LIST,
    UPDATE,
    DELETE;

    private static final Logger logger = LoggerFactory.getLogger(VoucherCommandType.class);

    public static VoucherCommandType findCommandType(String userCommand) {
        return Arrays.stream(VoucherCommandType.values())
                .filter(voucherCommandType -> voucherCommandType.name().equals(userCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("원인 : {} -> 에러 메시지 : {}", userCommand, NOT_FOUND_VOUCHER_COMMAND_TYPE.getMessage());
                    throw new IllegalArgumentException(NOT_FOUND_VOUCHER_COMMAND_TYPE.getMessage());
                });
    }
}
