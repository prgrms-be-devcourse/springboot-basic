package com.prgrms.springbootbasic.common.exception;

import java.text.MessageFormat;

public class VoucherTypeNotSupportedException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "Voucher type {0} is not supported.";

    public VoucherTypeNotSupportedException(String voucherType) {
        super(MessageFormat.format(MESSAGE_FORMAT, voucherType));
    }
}
