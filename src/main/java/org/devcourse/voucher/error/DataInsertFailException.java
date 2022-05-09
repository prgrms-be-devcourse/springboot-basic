package org.devcourse.voucher.error;

import org.springframework.dao.DataAccessException;

public class DataInsertFailException extends DataAccessException {
    public DataInsertFailException(String msg) {
        super(msg);
    }
}
