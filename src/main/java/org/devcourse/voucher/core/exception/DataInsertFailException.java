package org.devcourse.voucher.core.exception;

import org.springframework.dao.DataAccessException;

public class DataInsertFailException extends DataAccessException {
    public DataInsertFailException(String msg) {
        super(msg);
    }
}
