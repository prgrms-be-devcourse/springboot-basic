package org.prgrms.kdt.exception.customer;

public class CustomerException extends RuntimeException {

    public CustomerException() {
    }

    public CustomerException(String message) {
        super(message);
    }
}
