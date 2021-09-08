package org.prgrms.kdt.voucher.exception;

public class InvalidVoucherTypeException extends RuntimeException {
    public InvalidVoucherTypeException(String errorMessage) {
        super(errorMessage);
    }
}
