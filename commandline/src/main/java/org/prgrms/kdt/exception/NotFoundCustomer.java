package org.prgrms.kdt.exception;

public class NotFoundCustomer extends CustomerException {

    public NotFoundCustomer() {
    }

    public NotFoundCustomer(String message) {
        super(message);
    }
}
