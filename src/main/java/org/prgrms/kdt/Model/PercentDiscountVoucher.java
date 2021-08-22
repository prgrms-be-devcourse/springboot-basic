package org.prgrms.kdt.Model;

import org.prgrms.kdt.TypeStatus;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private  final TypeStatus type;

    public PercentDiscountVoucher(UUID voucherId, long percent, TypeStatus type) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.type=TypeStatus.Percent;
    }

    public TypeStatus getType() {
        return type;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public long getdiscount() {
        return percent;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}