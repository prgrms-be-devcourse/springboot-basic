package com.programmers.kwonjoosung.springbootbasicjoosung.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataAlreadyExistException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(DataAlreadyExistException.class);

    public DataAlreadyExistException(String data, String Table) {
        super(data + " is already exist in " + Table);
        logger.error("DataAlreadyExistException Data -> {}", data);
    }

}
