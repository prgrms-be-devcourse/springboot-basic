package com.devcourse.springbootbasic.application.constant;

public enum ErrorMessage {

    INVALID_MENU("올바른 메뉴를 선택해주세요."),
    INVALID_VOUCHER_TYPE("올바른 바우처 메뉴를 선택해주세요."),
    INVALID_LIST_MENU("올바른 목록 메뉴를 선택해주세요."),
    INVALID_VOUCHER_INFO("올바른 바우처 정보를 입력해주세요."),
    INAVLID_VOUCHER_INSERTION("바우처 생성에 실패했습니다."),
    INVALID_DISCOUNT_VALUE("부적절한 값입니다."),
    INVALID_FILE_ACCESS("부적절한 파일 접근입니다.");

    private final String messageText;

    ErrorMessage(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

}
