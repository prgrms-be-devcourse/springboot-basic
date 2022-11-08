package org.programmers.spbw1.voucher;

import java.util.UUID;


public class PercentDiscountVoucher implements Voucher{
    private final UUID Id;
    private final long percent;

    public PercentDiscountVoucher(UUID id, long percent) {
        Id = id;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherID() {
        return this.Id;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - percent) / 100;
    }

    @Override
    public String toString(){
        return "ID : " + this.Id + ", Type : Percent, Discount Amount : " + this.percent;
    }
}
