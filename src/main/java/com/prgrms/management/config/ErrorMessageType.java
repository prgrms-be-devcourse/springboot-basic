package com.prgrms.management.config;

public enum ErrorMessageType {
    NOT_COMMAND_TYPE(":존재하지 않는 명령어 타입입니다."),
    NOT_CUSTOMER_TYPE(":존재하지 않는 손님 타입입니다."),
    NOT_VOUCHER_TYPE(":존재하지 않는 바우처 타입입니다."),
    INCORRECT_NUMBER_FORMAT(":올바른 숫자 형식이 아닙니다."),
    IO_EXCEPTION("IO EXCEPTION이 발생했습니다."),
    DUPLICATECUSTOMEREMAIL("중복된 이메일입니다."),
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
