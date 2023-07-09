package com.programmers.springweekly.domain;

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
            throw new IllegalArgumentException("Input: " + type + ", 찾으시는 바우처 지갑 메뉴가 없습니다.");
        }
    }
}
