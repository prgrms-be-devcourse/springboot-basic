package com.prgms.vouchermanager.exception;

public enum ExceptionType {
    INVALID_FRONT_MENU("초기 메뉴 번호를 잘못 입력하셨습니다."),
    INVALID_VOUCHER_MENU("바우처 메뉴 번호를 잘못 입력하셨습니다."),
    INVALID_CUSTOMER_MENU("고객 메뉴 번호를 잘못 입력하셨습니다."),

    INVALID_VOUCHER_PERCENT("할인율은 최대 100%입니다."),
    INVALID_VOUCHER_INFO("쿠폰 정보를 잘못 입력하셨습니다."),

    INVALID_VOUCHER_TYPE("쿠폰 타입을 잘못 입력하셨습니다."),

    INVALID_READ_FILE("파일을 읽을 수 없습니다."),

    INVALID_WRITE_FILE("파일에 저장할 수 없습니다.");

    private final String message;

    ExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
