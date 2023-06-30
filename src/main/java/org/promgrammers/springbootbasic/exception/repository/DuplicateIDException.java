package org.promgrammers.springbootbasic.exception.repository;

public class DuplicateIDException extends RuntimeException {

    public DuplicateIDException(String message) {
        super(message);
    }
}
