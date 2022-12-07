package com.example.springbootbasic.exception.voucher;

public enum VoucherFactoryExceptionMessage {

    VOUCHER_FACTORY_EXCEPTION_MESSAGE("존재하지 않는 바우처 타입입니다.");

    private final String message;

    VoucherFactoryExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
