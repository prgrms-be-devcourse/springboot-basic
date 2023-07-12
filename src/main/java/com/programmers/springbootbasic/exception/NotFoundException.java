package com.programmers.springbootbasic.exception;

public class NotFoundException extends IllegalStateException {

    public NotFoundException(String message) {
        super(message);
    }
}