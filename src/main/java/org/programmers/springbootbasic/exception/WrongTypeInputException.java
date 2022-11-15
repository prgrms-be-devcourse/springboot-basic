package org.programmers.springbootbasic.exception;

public class WrongTypeInputException extends RuntimeException{

    public WrongTypeInputException() {}
    public WrongTypeInputException(String message) {
        super(message);
    }
}
