package org.promgrammers.springbootbasic.exception.repository;

public class InvalidFilePathException extends RuntimeException {

    public InvalidFilePathException(String message) {
        super(message);
    }
}
