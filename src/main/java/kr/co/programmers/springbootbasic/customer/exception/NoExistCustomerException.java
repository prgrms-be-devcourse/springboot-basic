package kr.co.programmers.springbootbasic.customer.exception;

public class NoExistCustomerException extends RuntimeException {
    public NoExistCustomerException(String message) {
        super(message);
    }
}
