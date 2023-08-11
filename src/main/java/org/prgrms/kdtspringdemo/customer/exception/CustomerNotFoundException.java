package org.prgrms.kdtspringdemo.customer.exception;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(CustomerExceptionMessage message) {
        super(message.getMessage());
    }

    public CustomerNotFoundException(UUID id, CustomerExceptionMessage message) {
        super(message.getMessage());
    }

    public CustomerNotFoundException(String nickname, CustomerExceptionMessage message) {
        super(message.getMessage());
    }
}
