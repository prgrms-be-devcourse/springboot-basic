package com.programmers.exception;

public class NotFoundException extends IllegalStateException {

    public NotFoundException(String message) {
        super(message);
    }
}