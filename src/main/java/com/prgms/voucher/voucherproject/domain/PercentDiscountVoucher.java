package com.prgms.voucher.voucherproject.domain;


import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;


    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if(percent < 1 || percent > 99){
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
