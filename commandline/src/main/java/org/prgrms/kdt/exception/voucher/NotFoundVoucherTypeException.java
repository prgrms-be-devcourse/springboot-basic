package org.prgrms.kdt.exception.voucher;

import org.prgrms.kdt.exception.voucher.VoucherUserException;

public class NotFoundVoucherTypeException extends VoucherUserException {
    public NotFoundVoucherTypeException() {
    }

    public NotFoundVoucherTypeException(String message) {
        super(message);
    }
}
