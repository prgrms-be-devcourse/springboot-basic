package org.programmers.springboot.basic.domain.customer.exception;

public class BlackCustomerNotFoundException extends RuntimeException {

    public BlackCustomerNotFoundException() {
        super("No matching BLACK customers found!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
