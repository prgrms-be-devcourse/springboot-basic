package org.programmers.springbootbasic.exception;

public class CustomInvalidNameException extends RuntimeException {
    public CustomInvalidNameException(String message) {
        super(message);
    }
}
