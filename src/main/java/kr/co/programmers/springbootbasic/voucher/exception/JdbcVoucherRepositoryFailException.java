package kr.co.programmers.springbootbasic.voucher.exception;

public class JdbcVoucherRepositoryFailException extends RuntimeException {
    public JdbcVoucherRepositoryFailException(String message) {
        super(message);
    }
}
