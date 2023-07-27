package org.programmers.VoucherManagement.voucher.exception;

import lombok.Getter;
import org.programmers.VoucherManagement.global.response.ErrorCode;

@Getter
public class VoucherException extends RuntimeException {
    private final ErrorCode errorCode;

    public VoucherException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
