package com.programmers.vouchermanagement.customer.domain;

public enum CustomerType {
    BLACK, WHITE;

    public static CustomerType from(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("고객 명단에 존재하지 않습니다.");
        }
    }
}
