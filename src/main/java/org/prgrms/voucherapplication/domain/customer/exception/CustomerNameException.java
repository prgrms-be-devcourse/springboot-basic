package org.prgrms.voucherapplication.domain.customer.exception;

import org.prgrms.voucherapplication.global.exception.BusinessException;
import org.prgrms.voucherapplication.global.exception.ExceptionCode;

public class CustomerNameException extends BusinessException {
    public CustomerNameException(ExceptionCode code) {
        super(code);
    }
}
