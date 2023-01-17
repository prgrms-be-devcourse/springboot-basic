package com.programmers.voucher.exception;

public enum ErrorMessage {
    WRONG_ORDER_MESSAGE("잘못된 입력입니다."),
    EMPTY_VOUCHER_MESSAGE("조회되는 voucher가 없습니다."),
    NOT_EXIST_VOUCHER("존재하지 않는 바우처입니다."),
    NOT_EXIST_OR_NOT_ASSIGN_VOUCHER("존재하지 않거나 아직 할당되지 않은 바우처입니다."),
    NOT_EXIST_CUSTOMER("존재하지 않는 사용자입니다."),
    ALREADY_EXIST_CUSTOMER("이미 가입된 사용자입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
