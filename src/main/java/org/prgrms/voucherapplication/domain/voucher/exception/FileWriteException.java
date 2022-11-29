package org.prgrms.voucherapplication.domain.voucher.exception;

import org.prgrms.voucherapplication.global.exception.BusinessException;
import org.prgrms.voucherapplication.global.exception.ExceptionCode;

public class FileWriteException extends BusinessException {
    public FileWriteException(ExceptionCode code) {
        super(code);
    }
}
