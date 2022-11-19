package org.programmers.program.voucher.model;

import java.time.LocalDate;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher{
    public FixedAmountVoucher(UUID id, Long discountAmount){
        super(id, discountAmount);
    }
    public FixedAmountVoucher(UUID id, Long discountAmount, LocalDate expirationDate){
        super(id, discountAmount, expirationDate);
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
