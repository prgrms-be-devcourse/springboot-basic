package org.programmers.springboot.basic.domain.customer.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {
        super("Exception Occurred: No matching customers found!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
