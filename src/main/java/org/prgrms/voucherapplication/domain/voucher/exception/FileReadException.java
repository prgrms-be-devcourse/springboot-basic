package org.prgrms.voucherapplication.domain.voucher.exception;

import org.prgrms.voucherapplication.global.exception.BusinessException;
import org.prgrms.voucherapplication.global.exception.ExceptionCode;

public class FileReadException extends BusinessException {
    public FileReadException(ExceptionCode code) {
        super(code);
    }
}
