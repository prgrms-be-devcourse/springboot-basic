package org.promgrammers.springbootbasic.exception.repository;

public class DoesNotExistException extends RuntimeException {

    public DoesNotExistException(String message) {
        super(message);
    }
}
