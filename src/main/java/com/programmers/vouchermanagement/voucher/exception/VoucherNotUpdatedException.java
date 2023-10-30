package com.programmers.vouchermanagement.voucher.exception;

public class VoucherNotUpdatedException extends RuntimeException {

    public VoucherNotUpdatedException() {
        super("Voucher has not been updated properly. ");
    }
}
