package org.prgms.voucheradmin.global.exception;

import java.util.UUID;

public class VoucherWalletNotFoundException extends RuntimeException{

    public VoucherWalletNotFoundException(UUID customerId, UUID voucherId) {
        super("can't find a voucher wallet(customer_id: "+customerId+", voucher_id:"+voucherId+")");
    }

    public VoucherWalletNotFoundException(String message) {
        super(message);
    }

    public VoucherWalletNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VoucherWalletNotFoundException(Throwable cause) {
        super(cause);
    }

    public VoucherWalletNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
