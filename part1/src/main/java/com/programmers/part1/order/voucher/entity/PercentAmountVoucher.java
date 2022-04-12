package com.programmers.part1.order.voucher.entity;

import com.programmers.part1.error.voucher.PercentErrorException;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;

    public PercentAmountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        if(percent > 100)
            throw new PercentErrorException("비율은 100%를 넘길 수 없습니다.\n");
        else if(percent <= 0)
            throw new PercentErrorException("비율은 0%보다 커야합니다.\n");
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return String.format("%-20s %-6d", "PercentAmountVoucher", this.percent);
    }
}
