package com.blessing333.springbasic.domain.voucher.converter;

import com.blessing333.springbasic.domain.voucher.model.VoucherCreateFailException;

public class ConvertFailException extends VoucherCreateFailException {
    public ConvertFailException(String message) {
        super(message);
    }
}
