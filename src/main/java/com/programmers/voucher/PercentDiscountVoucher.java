package com.programmers.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{

    private final String type = "PercentDiscount";
    private final UUID voucherID;
    private final long discountPercent;

    public PercentDiscountVoucher(UUID voucherID, long discountPercent) {
        if(discountPercent<0 || discountPercent>100) throw new IllegalArgumentException();
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
    
    @Override
    public String toString() {
        return "Voucher ID -> " + voucherID + " Voucher Type -> " + type + " Discount -> " + discountPercent;
    }
}
