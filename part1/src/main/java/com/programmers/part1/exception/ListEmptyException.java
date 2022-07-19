package com.programmers.part1.exception;

public class ListEmptyException extends RuntimeException{

    public ListEmptyException(String message) {
        super(message);
    }
}
