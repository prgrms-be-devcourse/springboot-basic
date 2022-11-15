package org.programmers.springbootbasic.exception;

public class WrongCommandInputException extends RuntimeException {
    public WrongCommandInputException() {
    }

    public WrongCommandInputException(String message) {
        super(message);
    }
}
