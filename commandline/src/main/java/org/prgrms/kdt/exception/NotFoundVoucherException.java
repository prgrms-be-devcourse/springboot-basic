package org.prgrms.kdt.exception;

public class NotFoundVoucherException extends RuntimeException {

    public NotFoundVoucherException() {
    }

    public NotFoundVoucherException(String message) {
        super(message);
    }
}
