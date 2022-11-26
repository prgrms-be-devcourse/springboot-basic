package org.prgrms.kdt.exception.voucher;

import org.prgrms.kdt.exception.voucher.VoucherUserException;

public class InputException extends VoucherUserException {

    public InputException() {
    }

    public InputException(String message) {
        super(message);
    }
}
