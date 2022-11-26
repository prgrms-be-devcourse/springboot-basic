package org.prgrms.kdt.exception.voucher;

import org.prgrms.kdt.exception.voucher.VoucherUserException;

public class NotFoundVoucherException extends VoucherUserException {

    public NotFoundVoucherException() {
    }

    public NotFoundVoucherException(String message) {
        super(message);
    }
}
