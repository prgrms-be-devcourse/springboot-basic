package kr.co.programmers.springbootbasic.voucher.exception;

public class VoucherFailException extends RuntimeException {
    public VoucherFailException(String message) {
        super(message);
    }
}
