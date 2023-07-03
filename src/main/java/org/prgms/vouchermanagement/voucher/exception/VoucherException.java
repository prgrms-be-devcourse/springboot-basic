package org.prgms.vouchermanagement.voucher.exception;

import lombok.Getter;

@Getter
public class VoucherException extends RuntimeException{

    public VoucherException(String message) {
        super(message);
    }
}
