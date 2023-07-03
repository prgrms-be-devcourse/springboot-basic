package kr.co.programmers.springbootbasic.voucher.exception;

public class NoValidDiscountPrice extends RuntimeException {
    public NoValidDiscountPrice(String message) {
        super(message);
    }
}
