package com.programmers.springweekly.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum WalletMenu {
    ASSIGN,
    DELETE,
    FINDALL,
    FINDBYCUSTOMER,
    FINDBYVOUCHER;

    public static WalletMenu from(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Input : {}, 사용자가 입력한 바우처 지갑 메뉴가 존재하지 않아서 발생한 예외 ", type);
            throw new IllegalArgumentException("Input: " + type + ", 찾으시는 바우처 지갑 메뉴가 없습니다.");
        }
    }

}
