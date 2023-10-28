package org.programmers.springboot.basic.domain.customer.exception;

public class IllegalEmailException extends RuntimeException {

    public IllegalEmailException() {
        super("Illegal Email Type!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
