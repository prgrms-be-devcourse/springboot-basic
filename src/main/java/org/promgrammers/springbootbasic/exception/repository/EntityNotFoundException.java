package org.promgrammers.springbootbasic.exception.repository;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
