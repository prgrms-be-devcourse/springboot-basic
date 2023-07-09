package org.promgrammers.springbootbasic.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //고객
    NOT_FOUND_CUSTOMER(404, "존재하지 않는 Customer입니다."),
    DUPLICATED_USERNAME(400, "이미 존재하는 Username입니다."),
    INVALID_USERNAME_MESSAGE(400, "사용자명은 특수 문자를 제외한 한글, 영어, 숫자만 가능합니다."),

    //바우처
    NOT_FOUND_VOUCHER(404, "Voucher가 존재하지 않습니다."),
    NO_VOUCHER_PROVIDED(404, "제공된 바우처가 없습니다."),
    DUPLICATED_VOUCHER(400, "이미 할당된 바우처 입니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}