package com.programmers.commandline.global.exception;

public class ConsoleException extends RuntimeException {

    public ConsoleException(String message, Exception e) {
        super(message, e);
    }
}
