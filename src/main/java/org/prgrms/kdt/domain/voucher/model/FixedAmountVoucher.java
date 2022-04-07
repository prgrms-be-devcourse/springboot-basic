package org.prgrms.kdt.domain.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final int discount;

    public FixedAmountVoucher(UUID voucherId, int discount) {
        this.voucherId = voucherId;
        this.discount = discount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - discount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", discount=" + discount +
                '}';
    }
}
