package kr.co.programmers.springbootbasic.exception;

public class VoucherSaveFailException extends RuntimeException {
    public VoucherSaveFailException(String message) {
        super(message);
    }
}
