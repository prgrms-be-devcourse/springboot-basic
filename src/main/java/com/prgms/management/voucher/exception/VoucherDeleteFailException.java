package com.prgms.management.voucher.exception;

public class VoucherDeleteFailException extends VoucherException {
    private static final long serialVersionUID = -4758479268854864594L;

    public VoucherDeleteFailException() {
        super("바우처 삭제에 실패하였습니다.");
    }
}
