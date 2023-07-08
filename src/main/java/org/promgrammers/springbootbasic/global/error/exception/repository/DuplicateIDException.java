package org.promgrammers.springbootbasic.global.error.exception.repository;

public class DuplicateIDException extends RuntimeException {

    public DuplicateIDException(String message) {
        super(message);
    }
}
