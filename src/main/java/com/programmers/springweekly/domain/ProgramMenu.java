package com.programmers.springweekly.domain;

public enum ProgramMenu {
    EXIT,
    CUSTOMER,
    VOUCHER,
    WALLET;

    public static ProgramMenu from(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Input: " + type + ", 찾으시는 프로그램 메뉴가 없습니다.");
        }
    }
}
