package com.devcourse.springbootbasic.application.constant;

public enum Message {

    // Exception
    INVALID_MENU("올바른 메뉴를 선택해주세요."),
    INVALID_VOUCHER_TYPE("올바른 바우처 메뉴를 선택해주세요."),
    INVALID_LIST_MENU("올바른 목록 메뉴를 선택해주세요."),
    INVALID_VOUCHER_INFO("올바른 바우처 정보를 입력해주세요."),
    INAVLID_VOUCHER_INSERTION("바우처 생성에 실패했습니다."),
    INVALID_DISCOUNT_VALUE("부적절한 값입니다. 0.0 ~ 100.0 사이의 값을 입력해주세요."),
    INVALID_FILE_ACCESS("부적절한 파일 접근입니다."),

    // Output
    START_GAME_PROMPT("=== Voucher Program ==="),
    END_GAME_PROMPT("프로그램을 종료합니다."),
    CREATION_DONE_PROMPT("바우처가 생성되었습니다."),
    VOUCHER_TYPE_PROMPT("--- 바우처 옵션 선택 ---"),
    LIST_MENU_PROMPT("--- 출력 옵션 선택 ---"),
    LIST_VOUCHERS_PROMPT("- 생성한 바우처 목록입니다. -"),
    BLACK_CUSTOMER_PROMPT("진상 목록입니다.");

    private final String messageText;

    Message(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

}
