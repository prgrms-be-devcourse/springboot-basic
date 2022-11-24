package org.programmers.springbootbasic.exception;

public class NotAnIntegerException extends RuntimeException {
    public NotAnIntegerException() {
    }

    public NotAnIntegerException(String message) {
        super(message);
    }
}
