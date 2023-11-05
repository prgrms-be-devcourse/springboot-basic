package com.prgms.vouchermanager.exception;

public enum ExceptionType {
    INVALID_FRONT_MENU("초기 메뉴 번호를 잘못 입력하셨습니다."),
    INVALID_VOUCHER_MENU("바우처 메뉴 번호를 잘못 입력하셨습니다."),
    INVALID_CUSTOMER_MENU("고객 메뉴 번호를 잘못 입력하셨습니다."),

    INVALID_VOUCHER_PERCENT("할인율은 최대 100%입니다."),
    INVALID_VOUCHER_INFO("쿠폰 정보를 잘못 입력하셨습니다."),

    INVALID_VOUCHER_TYPE("쿠폰 타입을 잘못 입력하셨습니다."),

    INVALID_READ_FILE("파일을 읽을 수 없습니다."),

    INVALID_WRITE_FILE("파일에 저장할 수 없습니다."),

    DUPLICATED_KEY("이미 등록된 쿠폰 번호입니다."),
    INVALID_VOUCHER_ID("존재하지 않는 쿠폰 ID입니다."),
    INVALID_CUSTOMER_INFO("고객정보가 너무 길거나 너무 짧습니다."),
    INVALID_CUSTOMER_ID("존재하지 않는 고객 ID입니다."),
    INVALID_WALLET_INFO("지갑에 저장할수 없는 정보입니다"),
    INVALID_WALLET_MENU("지갑 메뉴를 잘못 입력하셨습니다.");
    private final String message;

    ExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
