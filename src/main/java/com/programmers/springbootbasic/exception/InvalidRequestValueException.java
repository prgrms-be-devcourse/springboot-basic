package com.programmers.springbootbasic.exception;

public class InvalidRequestValueException extends IllegalArgumentException {

    public InvalidRequestValueException(String message) {
        super(message);
    }
}
