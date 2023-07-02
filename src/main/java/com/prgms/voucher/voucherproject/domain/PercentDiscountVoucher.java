package com.prgms.voucher.voucherproject.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final static long MIN_PERCENT = 1;
    private final static long MAX_PERCENT = 99;
    private final UUID voucherId;
    private final long percent;


    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent < MIN_PERCENT || percent > MAX_PERCENT) {
            throw new IllegalArgumentException("잘못된 퍼센트 할인 금액입니다.");
        }
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return "| UUID:" + getId() + "  | VoucherType: PercentVoucher | percent:" + percent + " |";
    }
}
