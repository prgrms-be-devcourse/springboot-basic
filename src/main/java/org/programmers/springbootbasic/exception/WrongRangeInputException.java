package org.programmers.springbootbasic.exception;

public class WrongRangeInputException extends Exception {

    public WrongRangeInputException() {}
    public WrongRangeInputException(String message) {
        super(message);
    }
}
