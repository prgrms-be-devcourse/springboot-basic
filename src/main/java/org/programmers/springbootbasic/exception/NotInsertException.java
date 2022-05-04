package org.programmers.springbootbasic.exception;

public class NotInsertException extends RuntimeException {
    public NotInsertException(String message) {
        super(message);
    }
}
