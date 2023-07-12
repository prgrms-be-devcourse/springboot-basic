package com.programmers.vouchermanagement.customer.domain;

import com.programmers.vouchermanagement.customer.exception.CustomerErrorCode;
import com.programmers.vouchermanagement.customer.exception.CustomerException;

public enum CustomerType {
    BLACK, WHITE;

    public static CustomerType from(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomerException(CustomerErrorCode.INVALID_CUSTOMER_TYPE);
        }
    }
}
