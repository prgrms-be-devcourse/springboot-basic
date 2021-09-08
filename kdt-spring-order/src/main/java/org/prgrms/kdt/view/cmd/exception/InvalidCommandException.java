package org.prgrms.kdt.view.cmd.exception;

public class InvalidCommandException extends RuntimeException{
    public InvalidCommandException(String errorMessage) {
        super(errorMessage);
    }
}
