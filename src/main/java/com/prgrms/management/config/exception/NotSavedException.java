package com.prgrms.management.config.exception;

public class NotSavedException extends RuntimeException {
    private final String message;

    public NotSavedException(String message) {
        this.message = message;
    }
}
