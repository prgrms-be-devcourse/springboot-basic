package com.programmers.kwonjoosung.springbootbasicjoosung.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class WrongVoucherTypeException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(WrongVoucherTypeException.class);

    public WrongVoucherTypeException(String voucherType) {
        super(MessageFormat.format(" \"{0}\"는 올바르지 못한 VoucherType 입니다.", voucherType));
        logger.error("Wrong VoucherType = {}", voucherType);
    }
}
