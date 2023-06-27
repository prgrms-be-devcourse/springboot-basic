package com.prgms.voucher.voucherproject.domain;

import com.prgms.voucher.voucherproject.domain.Voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;


    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return null;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent/100);
    }

    @Override
    public String toString() {
        return "| UUID:" + getVoucherId() + "  | VoucherType: PercentVoucher | percent:" + percent +" |";
    }
}
