package kr.co.programmers.springbootbasic.customer.exception;

public class CustomerReadFailException extends RuntimeException {
    public CustomerReadFailException(String message) {
        super(message);
    }
}
