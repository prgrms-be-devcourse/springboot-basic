package org.prgms.voucherProgram.customer.exception;

public class WrongEmailException extends IllegalArgumentException {
    public WrongEmailException() {
        super();
    }

    public WrongEmailException(String message) {
        super(message);
    }
}
