package org.promgrammers.springbootbasic.exception;

public class DuplicateIDException extends RuntimeException {

    public DuplicateIDException(String message) {
        super(message);
    }
}
