package org.prgrms.voucherapplication.exception;

public class InvalidVoucherTypeException extends RuntimeException{
    public InvalidVoucherTypeException() {
        super(ErrorType.INVALID_VOUCHER_TYPE.getMessage());
    }

    public InvalidVoucherTypeException(String message) {
        super(message);
    }

    public InvalidVoucherTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVoucherTypeException(Throwable cause) {
        super(cause);
    }

    public InvalidVoucherTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
