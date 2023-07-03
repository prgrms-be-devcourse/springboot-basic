package kr.co.programmers.springbootbasic.voucher.exception;

public class NoValidCommandException extends RuntimeException {
    public NoValidCommandException(String s) {
        super(s);
    }
}
