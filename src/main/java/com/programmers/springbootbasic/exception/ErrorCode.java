package com.programmers.springbootbasic.exception;

public enum ErrorCode {
    FILE_IO_ERROR("파일 에러입니다. 시스템을 종료합니다."),
    INVALID_MENU("올바르지 않은 메뉴 타입입니다."),
    INVALID_VOUCHER("올바르지 않은 바우처 값 입니다."),
    INVALID_FIXED_VOUCHER_BENEFIT("고정 할인 금액은 0원 이상이어야 합니다."),
    INVALID_PERCENT_VOUCHER_BENEFIT("비율 할인 금액은 0% 이상 100% 이하여야 합니다."),
    INVALID_FILE_PATH("올바르지 않은 파일 경로입니다."),
    ;

    ErrorCode(String message) {
        this.message = message;
    }

    private final String message;

    public String getMessage() {
        return message;
    }
}
