package org.promgrammers.springbootbasic.exception;

public class EmptyListException extends RuntimeException {

    public EmptyListException(String message) {
        super(message);
    }
}
