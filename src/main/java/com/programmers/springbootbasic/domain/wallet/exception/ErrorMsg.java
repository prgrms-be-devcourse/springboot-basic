package com.programmers.springbootbasic.domain.wallet.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMsg {
    WALLET_ALREADY_EXIST("이미 지갑에 있는 Voucher입니다."),
    WALLET_NOT_FOUND("지갑에 Voucher가 존재하지 않습니다.");
    private final String message;
}
