package kr.co.programmers.springbootbasic.exception;

public class NoValidAmountException extends RuntimeException {
    public NoValidAmountException(String message) {
        super(message);
    }
}
