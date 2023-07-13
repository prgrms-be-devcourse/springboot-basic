package kr.co.programmers.springbootbasic.customer.exception;

public class CustomerCreateFailException extends RuntimeException {
    public CustomerCreateFailException(String message) {
        super(message);
    }
}
