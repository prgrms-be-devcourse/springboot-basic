package org.promgrammers.springbootbasic.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //고객
    NOT_FOUND_CUSTOMER(404, "NOT_FOUND", "존재하지 않는 Customer입니다."),
    DUPLICATED_USERNAME(400, "BAD_REQUEST", "이미 존재하는 Username입니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
