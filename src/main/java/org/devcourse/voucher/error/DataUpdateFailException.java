package org.devcourse.voucher.error;

import org.springframework.dao.DataAccessException;

public class DataUpdateFailException extends DataAccessException {
    public DataUpdateFailException(String msg) {
        super(msg);
    }
}
