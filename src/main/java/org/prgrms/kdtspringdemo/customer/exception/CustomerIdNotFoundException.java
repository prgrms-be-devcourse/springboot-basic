package org.prgrms.kdtspringdemo.customer.exception;

public class CustomerIdNotFoundException extends RuntimeException {
    public CustomerIdNotFoundException(CustomerExceptionMessage message) {
        super(message.getMessage());
    }
}
