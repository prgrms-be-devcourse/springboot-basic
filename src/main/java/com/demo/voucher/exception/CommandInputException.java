package com.demo.voucher.exception;

public class CommandInputException extends IllegalArgumentException {
    private static final String COMMAND_INPUT_EXCEPTION_MESSAGE = "올바른 명령어를 입력하지 않았습니다.";

    public CommandInputException() {
        super(COMMAND_INPUT_EXCEPTION_MESSAGE);
    }
}
