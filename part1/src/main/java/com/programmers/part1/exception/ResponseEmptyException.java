package com.programmers.part1.exception;

public class ResponseEmptyException extends RuntimeException{

    public ResponseEmptyException(String message) {
        super(message);
    }
}
