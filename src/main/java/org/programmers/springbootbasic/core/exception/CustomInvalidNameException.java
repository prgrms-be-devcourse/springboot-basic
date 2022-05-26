package org.programmers.springbootbasic.core.exception;

public class CustomInvalidNameException extends RuntimeException {
    public CustomInvalidNameException(String message) {
        super(message);
    }
}
