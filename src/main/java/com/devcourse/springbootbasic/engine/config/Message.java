package com.devcourse.springbootbasic.engine.config;

public record Message() {

    // Exception
    public static final String INVALID_MENU = "올바른 메뉴를 선택해주세요.";
    public static final String INVALID_VOUCHER_TYPE = "올바른 바우처 메뉴를 선택해주세요.";
    public static final String INVALID_LIST_MENU = "올바른 목록 메뉴를 선택해주세요.";
    public static final String INVALID_VOUCHER_INFO = "올바른 바우처 정보를 입력해주세요.";
    public static final String INVALID_DISCOUNT_VALUE = "부적절한 값입니다. 0.0 ~ 100.0 사이의 값을 입력해주세요.";
    public static final String INVALID_FILE_ACCESS = "부적절한 파일 접근입니다.";

    // Output
    public static final String END_GAME = "\n프로그램을 종료합니다.";
    public static final String CREATION_DONE = "\n바우처가 생성되었습니다.";
    public static final String LIST_VOUCHERS = "\n-Your Voucher List-";
    public static final String BLACK_CUSTOMER = "\n진상 목록입니다.";
}
