package org.programmers.springbootbasic.exception;

public class WrongRangeInputException extends RuntimeException {

    public WrongRangeInputException() {}
    public WrongRangeInputException(String message) {
        super(message);
    }
}
