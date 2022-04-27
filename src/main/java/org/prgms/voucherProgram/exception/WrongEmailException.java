package org.prgms.voucherProgram.exception;

public class WrongEmailException extends IllegalArgumentException {
    public WrongEmailException() {
        super();
    }

    public WrongEmailException(String message) {
        super(message);
    }
}
