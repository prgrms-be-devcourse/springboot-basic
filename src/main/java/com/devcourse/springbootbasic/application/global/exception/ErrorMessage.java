package com.devcourse.springbootbasic.application.global.exception;

public enum ErrorMessage {

    INVALID_MENU("올바른 메뉴가 아닙니다."),
    INVALID_PROPERTY("해당 정보가 없습니다."),
    INVALID_CREATION("생성이 실패했습니다."),
    INVALID_UPDATE("갱신이 실패했습니다."),
    INVALID_DISCOUNT_VALUE("부적절한 바우처 할인값입니다."),
    INVALID_FILE_ACCESS("부적절한 파일 접근입니다."),
    INVALID_SQL("부적절한 SQL 사용입니다.");

    private final String messageText;

    ErrorMessage(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

}
