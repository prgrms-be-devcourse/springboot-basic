package com.programmers.message;

public enum ErrorMessage {

    ERROR_INPUT_MESSAGE("잘못 입력하셨습니다." + System.lineSeparator()),
    VOUCHER_INPUT_ERROR_MESSAGE("입력하신 바우처의 값과 타입을 확인해주세요." + System.lineSeparator()),
    VOUCHER_ID_NOT_FOUND("존재하지 않는 바우처입니다." + System.lineSeparator()),

    INSERT_ERROR("저장에 실패하였습니다." + System.lineSeparator()),
    DELETE_ERROR("삭제 실패하였습니다." + System.lineSeparator()),
    CUSTOMER_NOT_FOUND("사용자를 찾을 수 없습니다." + System.lineSeparator()),

    NOT_FOUND_ERROR("조회에 실패하였습니다." + System.lineSeparator()),
    OCCUPIED_EMAIL("이미 사용중인 이메일입니다." + System.lineSeparator()),

    DB_ERROR_LOG("Repository 예외 발생" + System.lineSeparator()),
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
