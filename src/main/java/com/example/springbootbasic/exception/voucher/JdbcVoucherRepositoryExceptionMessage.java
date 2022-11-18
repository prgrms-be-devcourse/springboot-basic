package com.example.springbootbasic.exception.voucher;

public enum JdbcVoucherRepositoryExceptionMessage {
    VOUCHER_TYPE_NULL_EXCEPTION("바우처 타입에 null이 들어올 수 없습니다.");

    private final String message;

    JdbcVoucherRepositoryExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
