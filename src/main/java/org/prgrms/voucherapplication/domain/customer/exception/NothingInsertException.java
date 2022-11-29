package org.prgrms.voucherapplication.domain.customer.exception;

import org.prgrms.voucherapplication.global.exception.BusinessException;
import org.prgrms.voucherapplication.global.exception.ExceptionCode;

public class NothingInsertException extends BusinessException {

    public NothingInsertException(ExceptionCode code) {
        super(code);
    }
}
