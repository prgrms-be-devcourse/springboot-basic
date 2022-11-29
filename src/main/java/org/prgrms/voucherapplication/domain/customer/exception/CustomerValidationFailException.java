package org.prgrms.voucherapplication.domain.customer.exception;

import org.prgrms.voucherapplication.global.exception.BusinessException;
import org.prgrms.voucherapplication.global.exception.ExceptionCode;

public class CustomerValidationFailException extends BusinessException {
    public CustomerValidationFailException(ExceptionCode code) {
        super(code);
    }
}
