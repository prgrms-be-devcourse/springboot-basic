package com.programmers.vouchermanagement.voucher.exception;

import java.util.UUID;

public class VoucherNotFoundException extends RuntimeException {

    public VoucherNotFoundException() {
        super("Voucher not found.");
    }

    public VoucherNotFoundException(UUID wrongId) {
        super("Voucher not found. Input : " + wrongId);
    }
}
