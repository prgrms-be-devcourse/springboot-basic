package org.prgrms.kdt.exception;

public class InvalidVoucherTypeException extends RuntimeException {
    public InvalidVoucherTypeException(String errorMessage) {
        super(errorMessage);
    }
}
