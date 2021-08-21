package org.prgrms.kdt.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(ErrorMessage message) {
        super(message.name());
    }
}
