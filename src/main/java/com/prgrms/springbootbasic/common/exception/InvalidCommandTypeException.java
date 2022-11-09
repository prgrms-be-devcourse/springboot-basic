package com.prgrms.springbootbasic.common.exception;

public class InvalidCommandTypeException extends RuntimeException{

    public InvalidCommandTypeException(String message) {
        super(message);
    }
}
