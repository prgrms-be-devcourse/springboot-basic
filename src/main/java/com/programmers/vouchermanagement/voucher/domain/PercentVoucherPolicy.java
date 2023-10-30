package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.exception.IllegalDiscountException;

public class PercentVoucherPolicy implements VoucherPolicy {

    private static final Long MIN_PERCENT_DISCOUNT = 0L;
    private static final Long MAX_PERCENT_DISCOUNT = 100L;

    private final Long discount;

    public PercentVoucherPolicy(Long discount) {
        this.discount = discount;
        validateDiscount();
    }

    @Override
    public void validateDiscount() {

        if (discount > MAX_PERCENT_DISCOUNT || discount < MIN_PERCENT_DISCOUNT) {
            throw new IllegalDiscountException("Percent discounts are available between 0 and 100. ");
        }
    }

    @Override
    public Long getDiscount() {
        return discount;
    }
}
