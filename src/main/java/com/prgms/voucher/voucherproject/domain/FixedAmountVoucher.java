package com.prgms.voucher.voucherproject.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if(amount <= 0){
            throw new IllegalArgumentException("잘못된 고정 할인 금액입니다.");
        }
        this.amount = amount;
        this.voucherId = voucherId;
    }

    @Override
    public UUID getId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "| UUID:" + getId() + "  | VoucherType: FixedVoucher | amount:" + amount + " |";
    }


}
