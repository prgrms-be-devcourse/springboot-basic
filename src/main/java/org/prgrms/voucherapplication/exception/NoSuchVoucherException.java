package org.prgrms.voucherapplication.exception;

import java.text.MessageFormat;
import java.util.UUID;

public class NoSuchVoucherException extends RuntimeException {
    public NoSuchVoucherException() {
        super(ErrorType.NO_SUCH_VOUCHER.getMessage());
    }

    public NoSuchVoucherException(UUID voucherId) {
        super(MessageFormat.format("{0} Voucher ID: {1}", ErrorType.NO_SUCH_VOUCHER.getMessage(), voucherId));
    }

    public NoSuchVoucherException(String message) {
        super(message);
    }

    public NoSuchVoucherException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchVoucherException(Throwable cause) {
        super(cause);
    }

    public NoSuchVoucherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
