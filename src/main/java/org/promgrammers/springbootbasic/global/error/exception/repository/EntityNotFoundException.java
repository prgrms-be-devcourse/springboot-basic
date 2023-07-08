package org.promgrammers.springbootbasic.global.error.exception.repository;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
