package com.programmers.springbasic.domain.voucher.validator;

import lombok.Getter;


@Getter
public class VoucherCommandValidator {
    private static final String VALID_COMMAND_REGEXP = "^(exit|create|list|read|update|delete|show)$";  // exit, create, list 허용
    private static final String INVALID_COMMAND_MESSAGE = "지원하지 않는 command입니다.";

    private String inputCommand;

    public static void validateCommand(String inputCommand) {
        if (!inputCommand.matches(VALID_COMMAND_REGEXP)) {
            throw new IllegalArgumentException(INVALID_COMMAND_MESSAGE);
        }
    }
}
