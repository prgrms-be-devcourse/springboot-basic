package com.programmers.part1.exception;

public class CommandTypeMissingException extends RuntimeException{

    public CommandTypeMissingException(String message) {
        super(message);
    }
}
