package com.example.springbootbasic.exception.voucher;

public enum VoucherExceptionMessage {

    NULL_VOUCHER_TYPE_EXCEPTION("존재하지 않는 바우처 타입 입니다."),
    NULL_VOUCHER_FACTORY_EXCEPTION("생성할 수 없는 바우처 타입입니다.");

    private final String message;

    VoucherExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
