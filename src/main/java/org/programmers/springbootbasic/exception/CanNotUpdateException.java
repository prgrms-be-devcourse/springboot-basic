package org.programmers.springbootbasic.exception;

public class CanNotUpdateException extends RuntimeException {
    public CanNotUpdateException() {
    }

    public CanNotUpdateException(String message) {
        super(message);
    }

    public CanNotUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
