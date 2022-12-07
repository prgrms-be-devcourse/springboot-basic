package com.example.springbootbasic.exception.voucher;

public enum VoucherExceptionMessage {

    NULL_VOUCHER_TYPE_EXCEPTION("존재하지 않는 바우처 타입 입니다."),
    NULL_VOUCHER_FACTORY_EXCEPTION("생성할 수 없는 바우처 타입입니다."),
    WRONG_VOUCHER_ID_EXCEPTION("바우처 아이디는 0 이하 일 수 없습니다."),
    WRONG_VOUCHER_DISCOUNT_VALUE_EXCEPTION("바우처 할인 금액은 0 이하 일 수 없습니다."),
    WRONG_VOUCHER_TYPE_EXCEPTION("바우처 타입은 비어있을 수 없습니다.");

    private final String message;

    VoucherExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
