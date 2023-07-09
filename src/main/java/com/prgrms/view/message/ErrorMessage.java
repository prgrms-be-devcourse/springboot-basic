package com.prgrms.view.message;

public enum ErrorMessage {

    INVALID_SELECTION("올바른 선택지가 아닙니다."),
    NEGATIVE_ARGUMENT("양수여야 합니다."),
    NULL_ARGUMENT("null이 발생하였습니다."),
    NO_CONSTRUCTOR("생성자를 호출할 수 없습니다"),
    NOT_UPDATE("업데이트가 되지 않았습니다"),
    NO_RESULT_RETURN_EMPTY("반환된 값이 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
