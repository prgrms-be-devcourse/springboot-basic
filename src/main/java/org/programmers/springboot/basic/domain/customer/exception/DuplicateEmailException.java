package org.programmers.springboot.basic.domain.customer.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException() {
        super("Duplicate Email already exists!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
