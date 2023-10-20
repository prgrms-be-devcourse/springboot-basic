package com.programmers.springbootbasic.util;

public enum Messages {

    SELECT_MENU("=== Voucher Program ===" + System.lineSeparator()
        + "종료 : exit" + System.lineSeparator()
        + "바우처 등록 : create voucher" + System.lineSeparator()
        + "바우처 조회 : list voucher" + System.lineSeparator()
        + "유저 블랙리스트 조회 : list black user" + System.lineSeparator()
    ),
    VOUCHER_REGISTER_TYPE("등록할 바우처의 타입을 입력하세요.(FIXED, PERCENT)"),
    VOUCHER_REGISTER_AMOUNT("등록할 바우처의 금액을 입력하세요.(숫자)"),
    SUCCESS_VOUCHER_REGISTER("바우처가 등록되었습니다."),
    SUCCESS_VOUCHER_LIST("바우처 목록입니다."),
    SUCCESS_BLACK_USER_LIST("블랙리스트 유저 목록입니다."),
    ;

    Messages(String Message) {
        this.message = Message;
    }

    private String message;

    public String getMessage() {
        return message;
    }
}
