package kr.co.programmers.springbootbasic.voucher.exception;

public class NoValidAmountException extends RuntimeException {
    public NoValidAmountException(String message) {
        super(message);
    }
}
