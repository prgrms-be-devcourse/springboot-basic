package org.prgrms.kdt.exception.voucher;

import org.prgrms.kdt.exception.voucher.VoucherUserException;

public class NotFoundValueException extends VoucherUserException {

    public NotFoundValueException() {
    }

    public NotFoundValueException(String message) {
        super(message);
    }
}
