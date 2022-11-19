package org.programmers.program.voucher.model;


import java.util.UUID;

public class PercentDiscountVoucher extends Voucher{
    public PercentDiscountVoucher(UUID id, Long discountAmount){
        super(id, discountAmount);
    }

    @Override
    Long discount(Long price) {
        return price * (100 - discountAmount) / 100;
    }

    @Override
    public String toString() {
        return "voucher type : Percent, Discount amount : " + this.discountAmount;
    }
}
