package com.programmers.springbasic.domain.voucher.dto.request;

import lombok.Getter;


@Getter
public class CommandDTO {
    private static final String VALID_COMMAND_REGEXP = "^(exit|create|list)$";  // exit, create, list 허용
    private static final String INVALID_COMMAND_MESSAGE = "지원하지 않는 command입니다.";

    private String inputCommand;

    public CommandDTO(String inputCommand) {
        if (!inputCommand.matches(VALID_COMMAND_REGEXP)) {
            throw new IllegalArgumentException(INVALID_COMMAND_MESSAGE);
        }
        this.inputCommand = inputCommand;
    }
}
