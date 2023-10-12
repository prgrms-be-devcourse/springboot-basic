package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.exception.IllegalDiscountException;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final Long MIN_PERCENT_DISCOUNT = 0L;
    private static final Long MAX_PERCENT_DISCOUNT = 100L;

    private final UUID voucherId;
    private final Long discount;
    private final VoucherType voucherType;

    public PercentDiscountVoucher(UUID voucherId, Long discount, VoucherType voucherType) {
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

    public static void validateDiscount(Long discount) {

        if (discount > MAX_PERCENT_DISCOUNT || discount < MIN_PERCENT_DISCOUNT) {
            throw new IllegalDiscountException("Percent discounts are available between 0 and 100. ");
        }
    }
}
