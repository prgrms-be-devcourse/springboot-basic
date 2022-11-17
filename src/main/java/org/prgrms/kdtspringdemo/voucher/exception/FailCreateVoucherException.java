package org.prgrms.kdtspringdemo.voucher.exception;

public class FailCreateVoucherException extends RuntimeException {
    public FailCreateVoucherException() {
    }

    public FailCreateVoucherException(String message) {
        super("[비우쳐 생성 실패]%s".formatted(message));
    }

    public FailCreateVoucherException(String message, Throwable cause) {
        super("[비우쳐 생성 실패]%s".formatted(message), cause);
    }

    public FailCreateVoucherException(Throwable cause) {
        super(cause);
    }
}
