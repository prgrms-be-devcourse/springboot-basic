package com.prgms.voucher.voucherproject.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final static long MIN_AMOUNT = 0;
    private final static long MAX_AMOUNT = 1000000;

    private final UUID voucherId;
    private final long discount;

    public FixedAmountVoucher(long discount) {
        if (discount <= MIN_AMOUNT || discount >= MAX_AMOUNT) {
            throw new IllegalArgumentException("잘못된 고정 할인 금액입니다.");
        }
        this.discount = discount;
        this.voucherId = UUID.randomUUID();
    }

    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - this.discount;
        return discountedAmount < 0 ? 0 : discountedAmount;
    }

    @Override
    public UUID getId() {
        return this.voucherId;
    }

    @Override
    public long getDiscount() {
        return discount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

}
