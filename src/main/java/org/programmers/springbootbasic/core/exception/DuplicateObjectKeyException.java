package org.programmers.springbootbasic.core.exception;

public class DuplicateObjectKeyException extends RuntimeException {
    public DuplicateObjectKeyException(String message) {
        super(message);
    }
}
