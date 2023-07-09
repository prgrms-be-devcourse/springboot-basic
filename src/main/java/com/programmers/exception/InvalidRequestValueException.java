package com.programmers.exception;

public class InvalidRequestValueException extends IllegalArgumentException {

    public InvalidRequestValueException(String message) {
        super(message);
    }
}
