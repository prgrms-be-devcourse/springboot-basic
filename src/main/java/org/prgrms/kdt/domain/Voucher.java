package org.prgrms.kdt.domain;

import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final double discountAmount;

    public Voucher(VoucherType voucherType, double discountAmount) {
        this.voucherId = UUID.randomUUID();
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public String toString() {
        return "<ID : " + voucherId + " , VoucherType : " + voucherType + " , DiscountAmount : " + discountAmount + " >";
    }

    @Override
    public boolean equals(Object v) {
        if (!(v instanceof Voucher)) return false;
        return this.voucherId == ((Voucher) v).getVoucherId();
    }
}
