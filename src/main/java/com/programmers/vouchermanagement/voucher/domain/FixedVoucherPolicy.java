package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.exception.IllegalDiscountException;

public class FixedVoucherPolicy implements VoucherPolicy {

    private static final Long MIN_FIXED_DISCOUNT = 0L;

    public void validateDiscount(Long discount) {

        if (discount < MIN_FIXED_DISCOUNT) {
            throw new IllegalDiscountException("Fixed discounts are available from 0. ");
        }
    }
}
