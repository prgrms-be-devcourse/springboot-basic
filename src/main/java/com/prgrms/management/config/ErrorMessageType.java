package com.prgrms.management.config;

public enum ErrorMessageType {
    NOT_EXIST_COMMAND_TYPE(":존재하지 않는 명령어 타입입니다."),
    NOT_EXIST_CUSTOMER_TYPE(":존재하지 않는 손님 타입입니다."),
    NOT_EXIST_CUSTOMER_ID(":존재하지 않는 ID 입니다."),
    NOT_EXIST_VOUCHER_TYPE(":존재하지 않는 바우처 타입입니다."),
    INCORRECT_NUMBER_FORMAT(":올바른 숫자 형식이 아닙니다."),
    NOT_SAVED_EXCEPTION("저장에 실패했습니다."),
    DUPLICATE_CUSTOMER_EMAIL("중복된 이메일입니다."),
    NOT_EXIST_EXCEPTION("존재하지 않는 객체입니다."),
    NOT_EXECUTE_QUERY("쿼리 실행에 실패했습니다."),
    OUT_OF_RANGE_FIXED_NUMBER(":0~10000 이내로 입력하세요"),
    OUT_OF_RANGE_PERCENT_NUMBER(":0~100 이내로 입력하세요");


    private final String Message;

    ErrorMessageType(String message) {
        Message = message;
    }

    public String getMessage() {
        return Message;
    }
}
