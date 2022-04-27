package com.prgrms.management.config.exception;

public class DuplicatedEmailException extends RuntimeException {
    private final String message;

    public DuplicatedEmailException(String message) {
        this.message = message;
    }
}
