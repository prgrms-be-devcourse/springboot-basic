package com.programmers.springbootbasic.domain.customer.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMsg {
    EMAIL_TYPE_NOT_MATCH("잘못된 이메일 형식입니다."),
    ALREADY_IN_BLACKLIST("이미 Blacklist에 존재하는 회원입니다."),
    NOT_IN_BLACKLIST("Blacklist에 존재하지 않는 회원입니다."),
    CUSTOMER_NOT_FOUND("Customer를 찾을 수 없습니다."),
    CUSTOMER_ALREADY_EXIST("이미 존재하는 Email입니다.");
    private final String message;
}
