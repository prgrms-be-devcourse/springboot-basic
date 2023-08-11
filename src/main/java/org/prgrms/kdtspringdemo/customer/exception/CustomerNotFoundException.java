package org.prgrms.kdtspringdemo.customer.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(CustomerExceptionMessage message) {
        super(message.getMessage());
    }

    public CustomerNotFoundException(String reason, CustomerExceptionMessage message) {
        super(message.getMessage());
    }
}
