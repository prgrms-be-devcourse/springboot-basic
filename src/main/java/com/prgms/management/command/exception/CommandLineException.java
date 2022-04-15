package com.prgms.management.command.exception;

public class CommandLineException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "잘못된 입력입니다.";

    public CommandLineException() {
        super(DEFAULT_MESSAGE);
    }
}
