package com.prgms.management.voucher.exception;

public class VoucherNotSaveException extends VoucherException {
    private static final long serialVersionUID = 5655614177458447508L;

    public VoucherNotSaveException() {
        super("바우처 저장에 실패하였습니다.");
    }
}
