package org.prgrms.kdt.voucher.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private UUID walletId;
    private final VoucherType voucherType;

    public PercentDiscountVoucher(UUID voucherId, long percent, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherType = voucherType;
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
