package com.programmers.kwonjoosung.springbootbasicjoosung.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutOfRangeException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(OutOfRangeException.class);

    public OutOfRangeException(long discount, String Message) {
        super("This discount is Out Of Range -> " + discount + "\n" + Message);
        logger.error("OutOfRangeException input -> {}", discount);
    }
}
