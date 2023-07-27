package org.programmers.VoucherManagement.wallet.exception;

import lombok.Getter;
import org.programmers.VoucherManagement.global.response.ErrorCode;

@Getter
public class WalletException extends RuntimeException {
    private final ErrorCode errorCode;

    public WalletException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
