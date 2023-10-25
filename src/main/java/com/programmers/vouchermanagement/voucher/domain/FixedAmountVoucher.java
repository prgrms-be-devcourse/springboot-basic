package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.exception.IllegalDiscountException;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final Long MIN_FIXED_DISCOUNT = 0L;

    private final UUID voucherId;
    private final Long discount;
    private final VoucherType voucherType;

    public FixedAmountVoucher(UUID voucherId, Long discount, VoucherType voucherType) {

        validateDiscount(discount);

        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = voucherType;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public Long getDiscount() {
        return discount;
    }

    private void validateDiscount(Long discount) {

        if (discount < MIN_FIXED_DISCOUNT) {
            throw new IllegalDiscountException("Fixed discounts are available from 0. ");
        }
    }
}
