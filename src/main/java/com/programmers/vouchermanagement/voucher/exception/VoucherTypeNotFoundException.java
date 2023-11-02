package com.programmers.vouchermanagement.voucher.exception;

public class VoucherTypeNotFoundException extends RuntimeException {

    public VoucherTypeNotFoundException(String wrongType) {
        super("The corresponding voucher type does not exist. Input : " + wrongType);
    }
}
