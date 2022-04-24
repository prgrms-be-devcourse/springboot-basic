package org.prgms.voucheradmin.global.exception.customexception;

import java.util.UUID;

public class VoucherNotFoundException extends RuntimeException{
    private static final String VOUCHER_NOT_FOUND_EXCEPTION = "can't find a voucher";


    public VoucherNotFoundException(UUID voucherId) {
        super(VOUCHER_NOT_FOUND_EXCEPTION+" "+voucherId);
    }

    public VoucherNotFoundException(String message) {
        super(message);
    }

    public VoucherNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VoucherNotFoundException(Throwable cause) {
        super(cause);
    }

    public VoucherNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
