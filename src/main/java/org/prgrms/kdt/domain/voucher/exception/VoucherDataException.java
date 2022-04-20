package org.prgrms.kdt.domain.voucher.exception;

import org.prgrms.kdt.domain.common.exception.ExceptionType;
import org.prgrms.kdt.domain.common.exception.NotUpdatedException;

public class VoucherDataException extends NotUpdatedException {

    public VoucherDataException(ExceptionType errorType) {
        super(errorType);
    }
}
