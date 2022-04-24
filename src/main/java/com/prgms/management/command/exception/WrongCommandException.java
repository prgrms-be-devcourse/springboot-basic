package com.prgms.management.command.exception;

public class WrongCommandException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "잘못된 입력입니다.";

    public WrongCommandException() {
        super(DEFAULT_MESSAGE);
    }
}
