package com.programmers.exception;

public class EmptyException extends IllegalArgumentException {

    public EmptyException(String message) {
        super(message);
    }
}