package org.promgrammers.springbootbasic.exception.repository;

public class NonExistentDomainException extends RuntimeException {

    public NonExistentDomainException(String message) {
        super(message);
    }
}
