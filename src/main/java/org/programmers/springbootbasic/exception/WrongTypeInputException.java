package org.programmers.springbootbasic.exception;

public class WrongTypeInputException extends Exception{

    public WrongTypeInputException() {}
    public WrongTypeInputException(String message) {
        super(message);
    }
}
