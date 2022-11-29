package org.prgrms.voucherapplication.domain.voucher.exception;

import org.prgrms.voucherapplication.global.exception.BusinessException;
import org.prgrms.voucherapplication.global.exception.ExceptionCode;

public class EmptyResultException extends BusinessException {
    public EmptyResultException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
