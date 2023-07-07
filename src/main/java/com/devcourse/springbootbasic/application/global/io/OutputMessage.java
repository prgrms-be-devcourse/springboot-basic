package com.devcourse.springbootbasic.application.global.io;

public enum OutputMessage {

    START_GAME_PROMPT("=== Voucher Program ==="),
    END_GAME_PROMPT("프로그램을 종료합니다."),
    CREATION_DONE_PROMPT("바우처가 생성되었습니다."),
    VOUCHER_TYPE_PROMPT("--- 바우처 옵션 선택 ---"),
    LIST_MENU_PROMPT("--- 출력 옵션 선택 ---"),
    LIST_VOUCHERS_PROMPT("- 생성한 바우처 목록입니다. -"),
    BLACK_CUSTOMER_PROMPT("진상 목록입니다.");

    private final String messageText;

    OutputMessage(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

}
