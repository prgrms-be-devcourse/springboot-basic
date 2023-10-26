package com.programmers.vouchermanagement.voucher.exception;

public class VoucherNotFoundException extends RuntimeException {

    public VoucherNotFoundException() {
        super("Voucher not found. ");
    }
}
