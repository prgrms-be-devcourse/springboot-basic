package com.example.springbootbasic.exception;

public enum ParserExceptionMessage {
    CSV_PARSER_EXCEPTION("csv 파싱 중 오류가 발생했습니다.");

    private final String message;

    ParserExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
