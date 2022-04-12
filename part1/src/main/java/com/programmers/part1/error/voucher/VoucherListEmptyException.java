package com.programmers.part1.error.voucher;

public class VoucherListEmptyException extends RuntimeException{

    public VoucherListEmptyException(String message) {
        super(message);
    }
}
