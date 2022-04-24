package org.prgms.voucherProgram.domain.customer.exception;

public class WrongNameException extends IllegalArgumentException {
    public WrongNameException() {
        super();
    }

    public WrongNameException(String message) {
        super(message);
    }
}
