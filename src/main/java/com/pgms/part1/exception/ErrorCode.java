package com.pgms.part1.exception;

public enum ErrorCode {

    VOUCHER_NOT_EXIST(404,"Voucher를 찾을 수 없습니다."),
    CUSTOMER_NOT_EXIST(404,"Customer를 찾을 수 없습니다."),
    CUSTOMER_DUPLICATED(400,"이미 존재하는 Customer입니다."),
    INVALID_INPUT_DATA(400, "올바르지 않은 입력입니다.");

    private int status;
    private String message;

    ErrorCode(int i, String s) {
        status = i;
        message = s;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
