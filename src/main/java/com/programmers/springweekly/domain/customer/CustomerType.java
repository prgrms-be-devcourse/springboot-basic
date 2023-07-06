package com.programmers.springweekly.domain.customer;

public enum CustomerType {
    NORMAL,
    BLACKLIST;

    public static CustomerType from(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Input: " + type + ", 찾으시는 고객 타입이 없습니다.");
        }
    }

    public static boolean isBlacklistedCustomer(CustomerType customerType) {
        return customerType == CustomerType.BLACKLIST;
    }
}
