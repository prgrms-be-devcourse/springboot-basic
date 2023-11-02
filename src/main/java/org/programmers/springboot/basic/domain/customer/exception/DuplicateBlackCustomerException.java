package org.programmers.springboot.basic.domain.customer.exception;

public class DuplicateBlackCustomerException extends RuntimeException {

    public DuplicateBlackCustomerException() {
        super("Duplicate customer already exists in blacklist!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
