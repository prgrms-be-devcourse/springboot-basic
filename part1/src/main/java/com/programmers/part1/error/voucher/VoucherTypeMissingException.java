package com.programmers.part1.error.voucher;

/**
 * Client가 다른 voucher type을 요구하거나 올바르지 않은 입력을 했을 경우
 * */
public class VoucherTypeMissingException extends RuntimeException{

    public VoucherTypeMissingException(String message) {
        super(message);
    }
}
