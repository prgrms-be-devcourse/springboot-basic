package kr.co.programmers.springbootbasic.voucher.exception;

public class VoucherSaveFailException extends RuntimeException {
    public VoucherSaveFailException(String message) {
        super(message);
    }
}
