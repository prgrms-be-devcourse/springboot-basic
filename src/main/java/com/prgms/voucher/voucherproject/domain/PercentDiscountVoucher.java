package com.prgms.voucher.voucherproject.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MIN_PERCENT = 1;
    private static final long MAX_PERCENT = 99;

    private final UUID voucherId;
    private final long discount;

    public PercentDiscountVoucher(long discount) {
        if (discount < MIN_PERCENT || discount > MAX_PERCENT) {
            throw new IllegalArgumentException("잘못된 퍼센트 할인 금액입니다.");
        }
        this.voucherId = UUID.randomUUID();
        this.discount = discount;
    }

    @Override
    public UUID getId() {
        return this.voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (this.discount / 100);
    }

    @Override
    public long getDiscount() {
        return discount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

}
