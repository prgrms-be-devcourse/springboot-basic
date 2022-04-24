package org.prgms.voucherProgram.domain.customer.exception;

public class WrongEmailException extends IllegalArgumentException {
    public WrongEmailException() {
        super();
    }

    public WrongEmailException(String message) {
        super(message);
    }
}
