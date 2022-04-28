package com.blessing333.springbasic.domain.voucher.service.exception;

public class VoucherFindFailException extends VoucherServiceException{
    public VoucherFindFailException() {
        super("존재하지 않는 바우처입니다");
    }
}
