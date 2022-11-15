package org.programmers.springbootbasic.exception;

public class MinusDiscountResultException extends RuntimeException {
    public MinusDiscountResultException() {
    }

    public MinusDiscountResultException(String message) {
        super(message);
    }
}
