package com.programmers.kwonjoosung.springbootbasicjoosung.exception;

import java.text.MessageFormat;

public class WrongVoucherTypeException extends RuntimeException {
    public WrongVoucherTypeException(String voucherType) {
        super(MessageFormat.format(" \"{0}\"는 올바르지 못한 VoucherType 입니다.", voucherType));
    }
}
