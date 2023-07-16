package org.programmers.VoucherManagement.wallet.exception;

public class WalletException extends RuntimeException {
    public WalletException(WalletExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
