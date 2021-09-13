package org.prgrms.kdt.voucher.model;

import lombok.Data;

import java.util.UUID;

@Data
public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private UUID walletId;

    public PercentDiscountVoucher(UUID voucherId, long percent ) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public long getAmount() {
        return this.percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}
