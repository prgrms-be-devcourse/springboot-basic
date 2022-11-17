package org.prgrms.kdt.exceptions;

public class NoVoucherException extends RuntimeException{
    public NoVoucherException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoVoucherException(String message) {
        super(message);
    }
}
