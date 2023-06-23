package org.promgrammers.springbootbasic.exception;

public class EmptyVoucherListException extends RuntimeException {

    public EmptyVoucherListException(String message) {
        super(message);
    }
}
