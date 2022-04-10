package com.prgms.management.command.exception;

public class CommandLineException extends Exception{
    private static final long serialVersionUID = 4534666096718341325L;

    public CommandLineException() {
        super("잘못된 입력입니다.");
    }
}
