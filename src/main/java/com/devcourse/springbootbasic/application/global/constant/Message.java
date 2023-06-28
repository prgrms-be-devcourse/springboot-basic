package com.devcourse.springbootbasic.application.global.constant;

public record Message() {

    // Exception
    public static final String INVALID_MENU = "올바른 메뉴를 선택해주세요.";
    public static final String INVALID_VOUCHER_TYPE = "올바른 바우처 메뉴를 선택해주세요.";
    public static final String INVALID_LIST_MENU = "올바른 목록 메뉴를 선택해주세요.";
    public static final String INVALID_VOUCHER_INFO = "올바른 바우처 정보를 입력해주세요.";
    public static final String INAVLID_VOUCHER_INSERTION = "바우처 생성에 실패했습니다.";
    public static final String INVALID_DISCOUNT_VALUE = "부적절한 값입니다. 0.0 ~ 100.0 사이의 값을 입력해주세요.";
    public static final String INVALID_FILE_ACCESS = "부적절한 파일 접근입니다.";

    // Output
    public static final String START_GAME_PROMPT = "=== Voucher Program ===";
    public static final String END_GAME_PROMPT = "프로그램을 종료합니다.";
    public static final String CREATION_DONE_PROMPT = "바우처가 생성되었습니다.";
    public static final String VOUCHER_TYPE_PROMPT = "--- 바우처 옵션 선택 ---";
    public static final String LIST_MENU_PROMPT = "--- 출력 옵션 선택 ---";
    public static final String LIST_VOUCHERS_PROMPT = "- 생성한 바우처 목록입니다. -";
    public static final String BLACK_CUSTOMER_PROMPT = "진상 목록입니다.";
}
