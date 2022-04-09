package com.prgms.management.voucher.exception;

public class VoucherListEmptyException extends VoucherException {
    private static final long serialVersionUID = 5390369023118180897L;

    public VoucherListEmptyException() {
        super("저장된 바우처가 없습니다.");
    }
}
