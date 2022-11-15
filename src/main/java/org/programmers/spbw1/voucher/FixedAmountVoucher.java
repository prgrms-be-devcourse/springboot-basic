package org.programmers.spbw1.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher{

    public FixedAmountVoucher(UUID id, long amount) {
       super(id, amount);
    }

    @Override
    public UUID getVoucherID() {
        return this.id;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString(){
        return "ID : " + this.id + ", Type : Fixed Amount, Discount Amount : " + this.amount;
    }
}
