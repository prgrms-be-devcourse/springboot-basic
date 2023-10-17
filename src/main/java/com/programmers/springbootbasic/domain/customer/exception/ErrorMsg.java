package com.programmers.springbootbasic.domain.customer.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMsg {
    emailTypeNotMatch("잘못된 이메일 형식입니다."),
    alreadyInBlacklist("이미 Blacklist에 존재하는 회원입니다."),
    notInBlacklist("Blacklist에 존재하지 않는 회원입니다."),
    customerNotFound("Customer를 찾을 수 없습니다."),
    customerAlreadyExist("이미 존재하는 Email입니다.");
    private final String message;
}
