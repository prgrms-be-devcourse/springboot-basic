package com.example.springbootbasic.exception;

public enum ParserExceptionMessage {
    CSV_PARSER_EXCEPTION("csv 파싱 중 오류가 발생했습니다."),
    CSV_CUSTOMER_PARSER_EXCEPTION("고객 csv 파싱 중 [%s]에서 오류가 발생하였습니다."),
    CSV_VOUCHER_PARSER_EXCEPTION("바우처 csv 파싱 중 [%s]에서 오류가 발생하였습니다.");

    private final String message;

    ParserExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
