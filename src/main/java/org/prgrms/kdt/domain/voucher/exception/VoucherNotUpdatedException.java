package org.prgrms.kdt.domain.voucher.exception;

import org.prgrms.kdt.domain.common.exception.ExceptionType;
import org.prgrms.kdt.domain.common.exception.NotUpdatedException;

public class VoucherNotUpdatedException extends NotUpdatedException {

    public VoucherNotUpdatedException(ExceptionType errorType) {
        super(errorType);
    }
}
