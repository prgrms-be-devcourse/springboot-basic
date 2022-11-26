package org.prgrms.kdt.exception.voucher;

import org.prgrms.kdt.exception.voucher.VoucherUserException;

public class WrongRangeInputException extends VoucherUserException {
    public WrongRangeInputException() {
    }

    public WrongRangeInputException(String message) {
        super(message);
    }
}
