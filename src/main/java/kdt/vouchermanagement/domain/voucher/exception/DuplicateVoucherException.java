package kdt.vouchermanagement.domain.voucher.exception;

public class DuplicateVoucherException extends RuntimeException {
    public DuplicateVoucherException() {
    }

    public DuplicateVoucherException(String message) {
        super(message);
    }
}
