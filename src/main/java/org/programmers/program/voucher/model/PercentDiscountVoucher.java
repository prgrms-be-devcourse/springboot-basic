package org.programmers.program.voucher.model;


import org.programmers.program.voucher.controller.VoucherDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher{
    public PercentDiscountVoucher(VoucherDto dto){
        super(dto);
        this.voucherType = VoucherType.PERCENT;
    }
    public PercentDiscountVoucher(UUID id, Long discountAmount){
        super(id, discountAmount);
        this.voucherType = VoucherType.PERCENT;
    }
    public PercentDiscountVoucher(UUID id, Long discountAmount, LocalDateTime expirationDate){
        super(id, discountAmount, expirationDate);
        this.voucherType = VoucherType.PERCENT;

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
