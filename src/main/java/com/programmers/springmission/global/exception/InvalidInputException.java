package com.programmers.springmission.global.exception;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}

