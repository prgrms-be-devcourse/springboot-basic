package org.programmers.spbw1.voucher;

import java.util.UUID;


public class PercentDiscountVoucher extends Voucher{

    public PercentDiscountVoucher(UUID id, long percent) {
       super(id, percent);
    }

    @Override
    public UUID getVoucherID() {
        return this.id;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - amount) / 100;
    }

    @Override
    public String toString(){
        return "ID : " + this.id + ", Type : Percent, Discount Amount : " + this.amount;
    }
}
