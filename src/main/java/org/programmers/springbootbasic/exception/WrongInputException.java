package org.programmers.springbootbasic.exception;

public class WrongInputException extends Exception{

    public WrongInputException() {}
    public WrongInputException(String message) {
        super(message);
    }
}
