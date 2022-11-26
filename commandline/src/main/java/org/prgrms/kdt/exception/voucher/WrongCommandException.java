package org.prgrms.kdt.exception.voucher;

import org.prgrms.kdt.exception.voucher.VoucherUserException;

public class WrongCommandException extends VoucherUserException {
    public WrongCommandException() {
    }

    public WrongCommandException(String message) {
        super(message);
    }
}
