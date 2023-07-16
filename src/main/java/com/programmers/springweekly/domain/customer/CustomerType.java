package com.programmers.springweekly.domain.customer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum CustomerType {
    NORMAL,
    BLACKLIST;

    public static CustomerType from(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Input : {}, 사용자가 입력한 고객 타입이 존재하지 않아서 발생한 예외 ", type, e);
            throw new IllegalArgumentException("Input: " + type + ", 찾으시는 고객 타입이 없습니다.");
        }
    }

    public static boolean isBlacklistedCustomer(CustomerType customerType) {
        return customerType == CustomerType.BLACKLIST;
    }

}
