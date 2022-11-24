package org.programmers.springbootbasic.exception;

public class CanNotInsertException extends RuntimeException {
    public CanNotInsertException() {
    }

    public CanNotInsertException(String message) {
        super(message);
    }

    public CanNotInsertException(String message, Throwable cause) {
        super(message, cause);
    }
}
