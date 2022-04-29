package org.programmers.springbootbasic.exception;

public class NoIdException extends RuntimeException {
    public NoIdException(String message) {
        super(message);
    }
}
