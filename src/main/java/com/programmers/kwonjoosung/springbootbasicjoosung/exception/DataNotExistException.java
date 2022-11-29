package com.programmers.kwonjoosung.springbootbasicjoosung.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataNotExistException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(DataNotExistException.class);

    public DataNotExistException(String data, String Table) {
        super(data + " is not exist in " + Table);
        logger.error("DataNotExistException Data -> {}", data);
    }
    public DataNotExistException() {
        super("Data is not exist");
        logger.error("DataNotExistException Not found Any Data");
    }

}
