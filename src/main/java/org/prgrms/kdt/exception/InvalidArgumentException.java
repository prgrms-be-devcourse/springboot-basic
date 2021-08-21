package org.prgrms.kdt.exception;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(ErrorMessage message) {
        super(message.name());
    }
}
