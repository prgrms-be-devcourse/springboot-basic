package com.programmers.vouchermanagement.customer.domain;

import com.programmers.vouchermanagement.customer.exception.InvalidCustomerTypeException;

public enum CustomerType {
    BLACK, WHITE;

    public static CustomerType from(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCustomerTypeException("잘못된 고객 타입입니다.");
        }
    }
}
