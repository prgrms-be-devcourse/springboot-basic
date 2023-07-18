package com.programmers.vouchermanagement.customer.domain;

import com.programmers.vouchermanagement.global.exception.ErrorCode;
import com.programmers.vouchermanagement.voucher.exception.VoucherException;

public enum CustomerType {
    BLACK, WHITE;

    public static CustomerType from(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new VoucherException(ErrorCode.INVALID_CUSTOMER_TYPE);
        }
    }
}
