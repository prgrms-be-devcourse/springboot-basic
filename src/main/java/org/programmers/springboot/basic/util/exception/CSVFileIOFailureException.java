package org.programmers.springboot.basic.util.exception;

public class CSVFileIOFailureException extends RuntimeException {

    public CSVFileIOFailureException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
