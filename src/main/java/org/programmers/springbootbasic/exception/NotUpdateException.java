package org.programmers.springbootbasic.exception;

public class NotUpdateException extends RuntimeException {
    public NotUpdateException(String message) {
        super(message);
    }
}
