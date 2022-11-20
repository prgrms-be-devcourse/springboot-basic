package org.prgrms.kdt.exception;

public class NotFoundValue extends RuntimeException {

    public NotFoundValue() {
    }

    public NotFoundValue(String message) {
        super(message);
    }
}
