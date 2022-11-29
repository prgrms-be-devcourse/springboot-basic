package com.programmers.kwonjoosung.springbootbasicjoosung.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WrongVoucherTypeException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(WrongVoucherTypeException.class);

    public WrongVoucherTypeException(String inputVoucherType) {
        super("This is WrongVoucherType -> " + inputVoucherType);
        logger.error("WrongVoucherTypeException Type -> {}", inputVoucherType);
    }
}
