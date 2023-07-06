package kr.co.programmers.springbootbasic.voucher.exception;

public class FileVoucherRepositoryFailException extends RuntimeException {
    public FileVoucherRepositoryFailException(String message) {
        super(message);
    }
}
