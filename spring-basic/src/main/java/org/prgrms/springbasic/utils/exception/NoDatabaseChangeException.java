package org.prgrms.springbasic.utils.exception;

public class NoDatabaseChangeException extends RuntimeException {

    public NoDatabaseChangeException(String message) {
        super(message);
    }
}
