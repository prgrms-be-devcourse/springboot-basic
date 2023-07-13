package com.programmers.springweekly.domain;

public enum VoucherMenu {
    CREATE,
    SELECT;

    public static VoucherMenu from(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Input: " + type + ", 찾으시는 바우처 메뉴가 없습니다.");
        }
    }
}
