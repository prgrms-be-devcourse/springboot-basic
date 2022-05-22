package org.programmers.springbootbasic.core.exception;

public class NotFoundIdException extends RuntimeException {
    public NotFoundIdException(String message) {
        super(message);
    }
}
