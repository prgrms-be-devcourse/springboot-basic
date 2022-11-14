package com.prgrms.springbootbasic.common.exception;

import java.text.MessageFormat;

public class AmountOutOfBoundException extends IllegalArgumentException {
    private static final String MESSAGE_FORMAT = "Your voucher type: {0}, Voucher amount input out of bound {1} ~ {2}";

    public AmountOutOfBoundException(String voucherType, int minBoundary, int maxBoundary) {
        super(MessageFormat.format(MESSAGE_FORMAT, voucherType, minBoundary, maxBoundary));
    }
}
