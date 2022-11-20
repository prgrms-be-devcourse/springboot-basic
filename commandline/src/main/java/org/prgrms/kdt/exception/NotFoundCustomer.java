package org.prgrms.kdt.exception;

public class NotFoundCustomer extends RuntimeException {

    public NotFoundCustomer() {
    }

    public NotFoundCustomer(String message) {
        super(message);
    }
}
