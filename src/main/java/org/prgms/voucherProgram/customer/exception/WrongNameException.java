package org.prgms.voucherProgram.customer.exception;

public class WrongNameException extends IllegalArgumentException {
    public WrongNameException() {
        super();
    }

    public WrongNameException(String message) {
        super(message);
    }
}
