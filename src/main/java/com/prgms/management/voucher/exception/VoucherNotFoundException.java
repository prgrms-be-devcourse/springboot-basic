package com.prgms.management.voucher.exception;

public class VoucherNotFoundException extends VoucherException {
    private static final long serialVersionUID = -4411167074712897443L;

    public VoucherNotFoundException() {
        super("해당 정보에 일치하는 바우처가 없습니다.");
    }
}
