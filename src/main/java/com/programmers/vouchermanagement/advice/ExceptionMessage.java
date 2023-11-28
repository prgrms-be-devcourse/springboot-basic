package com.programmers.vouchermanagement.advice;

public class ExceptionMessage {
    private final String message;

    public ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
