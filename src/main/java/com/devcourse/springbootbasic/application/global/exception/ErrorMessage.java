package com.devcourse.springbootbasic.application.global.exception;

public enum ErrorMessage {

    INVALID_MENU("올바른 메뉴가 아닙니다."),
    INVALID_VOUCHER_TYPE("올바른 바우처 메뉴가 아닙니다."),
    INVALID_LIST_MENU("올바른 목록 메뉴가 아닙니다."),

    INVALID_VOUCHER_INFO("올바른 바우처 정보가 아닙니다."),
    INVALID_CUSTOMER_INFO("올바른 고객 정보가 아닙니다."),

    INAVLID_VOUCHER_CREATION("바우처 생성이 실패했습니다."),
    INVALID_CUSTOMER_CREATION("고객 추가에 실패했습니다."),

    INVALID_DISCOUNT_VALUE("부적절한 바우처 할인값입니다."),

    INVALID_FILE_ACCESS("부적절한 파일 접근입니다."),
    INVALID_SQL_QUERY("부적절한 쿼리 사용입니다."),
    INVALID_SQL_RESULT("쿼리 결과가 없습니다.");

    private final String messageText;

    ErrorMessage(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

}
