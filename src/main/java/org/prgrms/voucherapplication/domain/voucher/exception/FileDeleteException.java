package org.prgrms.voucherapplication.domain.voucher.exception;

import org.prgrms.voucherapplication.global.exception.BusinessException;
import org.prgrms.voucherapplication.global.exception.ExceptionCode;

public class FileDeleteException extends BusinessException {

    public FileDeleteException(ExceptionCode code) {
        super(code);
    }
}
