package kr.co.programmers.springbootbasic.voucher.exception;

public class FileSaveFailException extends RuntimeException {
    public FileSaveFailException(String message) {
        super(message);
    }
}
