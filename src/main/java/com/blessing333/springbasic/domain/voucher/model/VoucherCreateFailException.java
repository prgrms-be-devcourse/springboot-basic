package com.blessing333.springbasic.domain.voucher.model;

public class VoucherCreateFailException extends IllegalArgumentException{
    public VoucherCreateFailException(String message) {
        super(message);
    }
}
