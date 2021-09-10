package org.prgrms.kdt.application.cmd.exception;

public class InvalidCommandException extends RuntimeException{
    public InvalidCommandException(String errorMessage) {
        super(errorMessage);
    }
}
