package org.prgrms.voucherapplication.domain.voucher.entity;

import org.prgrms.voucherapplication.global.exception.BusinessException;
import org.prgrms.voucherapplication.global.exception.ExceptionCode;

public class VoucherTypeOfException extends BusinessException {
    public VoucherTypeOfException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
