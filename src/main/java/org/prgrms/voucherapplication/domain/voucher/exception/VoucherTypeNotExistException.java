package org.prgrms.voucherapplication.domain.voucher.exception;

import org.prgrms.voucherapplication.global.exception.BusinessException;
import org.prgrms.voucherapplication.global.exception.ExceptionCode;

public class VoucherTypeNotExistException extends BusinessException {
    public VoucherTypeNotExistException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
