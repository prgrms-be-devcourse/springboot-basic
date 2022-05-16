package com.mountain.voucherapp.common.constants;

public enum ErrorMessage {
    FILE_INSERT_ERROR("file insert error"),
    FILE_READ_ERROR("file read error"),
    NEGATIVE_AMOUNT_ERROR("0 이하의 수는 입력할 수 없습니다."),
    MAX_MORE_ERROR("최대 금액을 넘을 수 없습니다: "),
    EMPTY_RESULT_ERROR("결과가 존재하지 않습니다."),
    NOT_BLANK("공백이 될 수 없습니다."),
    EMAIL_OUT_OF_RANGE("이메일의 입력 범위(4~50)를 벗어났습니다."),
    EMAIL_NOT_VALID("이메일 형식이 올바르지 않습니다."),
    WRONG_VALUE("올바른 값을 입력해주세요."),
    NOT_INSERTED("Insert가 수행되지 않았습니다."),
    NOT_UPDATED("Update가 수행되지 않았습니다."),
    EMPTY_RESULT("조회 결과가 존재하지 않습니다."),
    EXIST_VOUCHER("존재하는 바우처 입니다."),
    NOT_IMPLEMENT("해당 함수는 미구현 상태입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
