package com.programmers.springweekly.domain;

public enum CustomerMenu {
    CREATE,
    UPDATE,
    DELETE,
    SELECT,
    BLACKLIST;

    public static CustomerMenu from(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Input: " + type + ", 찾으시는 고객 메뉴가 없습니다.");
        }
    }
    
}
