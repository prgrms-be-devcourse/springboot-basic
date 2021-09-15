package org.prgrms.kdt.exception;

public class NotInsertException extends RuntimeException {
    public NotInsertException(ErrorMessage message) {
        super(message.name());
    }
}
