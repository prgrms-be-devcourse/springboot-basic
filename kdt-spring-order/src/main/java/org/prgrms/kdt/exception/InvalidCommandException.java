package org.prgrms.kdt.exception;

public class InvalidCommandException extends RuntimeException{
    public InvalidCommandException(String errorMessage) {
        super(errorMessage);
    }
}
