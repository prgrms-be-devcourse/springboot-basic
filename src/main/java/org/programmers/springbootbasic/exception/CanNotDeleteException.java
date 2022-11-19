package org.programmers.springbootbasic.exception;

public class CanNotDeleteException extends RuntimeException {
    public CanNotDeleteException() {
    }

    public CanNotDeleteException(String message) {
        super(message);
    }

    public CanNotDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
