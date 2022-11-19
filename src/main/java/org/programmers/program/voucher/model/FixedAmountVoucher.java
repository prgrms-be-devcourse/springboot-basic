package org.programmers.program.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher{
    public FixedAmountVoucher(UUID id, Long discountAmount){
        super(id, discountAmount);
    }

    @Override
    Long discount(Long price) {
        return price - discountAmount;
    }

    @Override
    public String toString() {
        return "voucher type : Fixed Amount, Discount amount : " + this.discountAmount;
    }
}
