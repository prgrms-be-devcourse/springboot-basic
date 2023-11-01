package com.pgms.part1.exception;

public enum ErrorCode {

    VOUCHER_NOT_EXIST(404,"Voucehr를 찾을 수 없습니다."),
    CUSTOMER_NOT_EXIST(404,"Customer를 찾을 수 없습니다.");

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
