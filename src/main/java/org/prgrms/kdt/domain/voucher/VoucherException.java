package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.exception.ErrorMessage;

public class VoucherException extends RuntimeException{
    private final ErrorMessage errorMessage;

    public VoucherException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
