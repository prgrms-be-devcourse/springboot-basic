package org.prgrms.voucherapp.exception;

public class WrongAmountException extends RuntimeException {
    public WrongAmountException(String message) {
        super(message);
    }

}
