package com.blessing333.springbasic.voucher.domain;

public class VoucherCreateFailException extends IllegalArgumentException{
    public VoucherCreateFailException(String message) {
        super(message);
    }
}
