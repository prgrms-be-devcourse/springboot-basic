package com.programmers.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{

    private final String type = "PercentDiscount";
    private final UUID voucherID;
    private final long discountPercent;

    public PercentDiscountVoucher(UUID voucherID, long discountPercent) {
        this.voucherID = voucherID;
        this.discountPercent = discountPercent;
    }

    @Override
    public UUID getVoucherID() {
        return voucherID;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - discountPercent) / 100;
    }

    public String getType() {
        return type;
    }

    @Override
    public long getDiscount() {
        return discountPercent;
    }
}
