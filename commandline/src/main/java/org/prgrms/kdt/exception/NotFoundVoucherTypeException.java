package org.prgrms.kdt.exception;

public class NotFoundVoucherTypeException extends RuntimeException {
    public NotFoundVoucherTypeException() {
    }

    public NotFoundVoucherTypeException(String message) {
        super(message);
    }
}
