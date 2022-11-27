package com.example.springbootbasic.exception;

public enum ConsoleTypeExceptionMessage {
    CONSOLE_TYPE_FIND_EXCEPTION("{0}은 존재하지 않는 콘솔 메뉴 선택입니다.");

    private final String message;

    ConsoleTypeExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
