package com.prgrms.springbootbasic.common.exception;

import java.text.MessageFormat;

public class DataModifyingNotSupportedException extends RuntimeException {

    private static final String MESSAGE = "Data Modifying query {0} is not supported.";

    public DataModifyingNotSupportedException(String queryType){
        super(MessageFormat.format(MESSAGE, queryType));
    }
}
