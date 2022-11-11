package com.programmers.kwonjoosung.springbootbasicjoosung.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class WrongCommandException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(WrongVoucherTypeException.class);

    public WrongCommandException(String command) {
        super(MessageFormat.format(" \"{0}\" 명령어는 올바르지 못한 명령어 입니다.", command));
        logger.error("Wrong CommandType = {}", command);
    }

}
