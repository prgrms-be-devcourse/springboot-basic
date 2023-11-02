package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.exception.IllegalDiscountException;

public class FixedVoucherPolicy implements VoucherPolicy {

    private static final Long MIN_FIXED_DISCOUNT = 0L;

    private final Long discount;

    public FixedVoucherPolicy(Long discount) {
        this.discount = discount;
        validateDiscount();
    }

    @Override
    public void validateDiscount() {

        if (discount < MIN_FIXED_DISCOUNT) {
            throw new IllegalDiscountException("Fixed discounts are available from 0. Input : " + discount);
        }
    }

    @Override
    public Long getDiscount() {
        return discount;
    }
}
