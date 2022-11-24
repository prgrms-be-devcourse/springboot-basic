package org.programmers.springbootbasic.exception;

public class InputException extends RuntimeException {
    public InputException() {
    }

    public InputException(String message) {
        super(message);
    }
}
