package com.programmers.kwonjoosung.springbootbasicjoosung.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WrongFindDataException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(WrongFindDataException.class);

    public WrongFindDataException(String message) {
        super(message);
        logger.error(message);
    }
}
