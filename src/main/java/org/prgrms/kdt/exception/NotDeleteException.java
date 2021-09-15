package org.prgrms.kdt.exception;

public class NotDeleteException extends RuntimeException {
    public NotDeleteException(ErrorMessage message) {
        super(message.name());
    }
}
