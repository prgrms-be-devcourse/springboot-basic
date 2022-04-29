package org.programmers.springbootbasic.exception;

public class DuplicateObjectKeyException extends RuntimeException {
    public DuplicateObjectKeyException(String message) {
        super(message);
    }
}
