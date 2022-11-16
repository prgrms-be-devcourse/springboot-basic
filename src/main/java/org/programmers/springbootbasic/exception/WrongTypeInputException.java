package org.programmers.springbootbasic.exception;

public class WrongTypeInputException extends RuntimeException {

    public WrongTypeInputException() {
    }

    public WrongTypeInputException(String message) {
        super(message);
    }

    public WrongTypeInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
