package com.prgrms.management.config.exception;

public class NotExistException extends RuntimeException {
    private final String message;

    public NotExistException(String message) {
        this.message = message;
    }
}
