package kr.co.programmers.springbootbasic.voucher.exception;

public class NoValidDiscountException extends RuntimeException {
    public NoValidDiscountException(String message) {
        super(message);
    }
}
