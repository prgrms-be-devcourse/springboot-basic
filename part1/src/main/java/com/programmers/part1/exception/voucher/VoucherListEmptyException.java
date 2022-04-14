package com.programmers.part1.exception.voucher;

public class VoucherListEmptyException extends RuntimeException{

    public VoucherListEmptyException(String message) {
        super(message);
    }
}
