package com.prgrms.view.message;

public enum ErrorMessage {

    INVALID_SELECTION("올바른 선택지가 아닙니다."),
    Negative_ARGUMENT ("양수여야 합니다.");

    private final String message;

    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
