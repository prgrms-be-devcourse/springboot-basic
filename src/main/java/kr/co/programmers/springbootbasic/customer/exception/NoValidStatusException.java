package kr.co.programmers.springbootbasic.customer.exception;

public class NoValidStatusException extends RuntimeException {
    public NoValidStatusException(String message) {
        super(message);
    }
}
