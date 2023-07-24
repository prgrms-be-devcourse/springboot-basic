package org.promgrammers.springbootbasic.global.error.exception.repository;

public class InvalidFilePathException extends RuntimeException {

    public InvalidFilePathException(String message) {
        super(message);
    }
}
