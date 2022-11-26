package org.prgrms.kdt.exception.customer;


public class NotFoundCustomerException extends CustomerException {

    public NotFoundCustomerException() {
    }

    public NotFoundCustomerException(String message) {
        super(message);
    }
}
