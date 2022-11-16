package org.prgrms.java.exception;

public class CommandException extends IllegalArgumentException {
    public CommandException(String message) {
        super(message);
    }
}
