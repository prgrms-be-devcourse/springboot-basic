package com.programmers.part1.order.voucher.entity;

import com.programmers.part1.error.voucher.FixedAmountException;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherID;
    private final long amount;

    public FixedAmountVoucher(UUID voucherID, long amount) {
        this.voucherID = voucherID;
        if(amount <= 0)
            throw new FixedAmountException("고정 금액 할인은 0원보다 크게 입력해야합니다.\n");
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherID;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-6d", "FixedAmountVoucher", this.amount);
    }
}
