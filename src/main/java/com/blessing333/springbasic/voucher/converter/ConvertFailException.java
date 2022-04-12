package com.blessing333.springbasic.voucher.converter;

import com.blessing333.springbasic.voucher.exception.VoucherCreateFailException;

public class ConvertFailException extends VoucherCreateFailException {
    public ConvertFailException(String message) {
        super(message);
    }
}
