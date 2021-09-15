package org.prgrms.kdt.exception;

public class NotUpdateException extends RuntimeException {
    public NotUpdateException(ErrorMessage message) {
        super(message.name());
    }
}
