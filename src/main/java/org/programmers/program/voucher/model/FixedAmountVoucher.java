package org.programmers.program.voucher.model;

import lombok.Builder;
import org.programmers.program.voucher.controller.VoucherDto;

import java.time.LocalDateTime;
import java.util.UUID;


// @Builder
public class FixedAmountVoucher extends Voucher{
    public FixedAmountVoucher(UUID id, Long discountAmount){
        super(id, discountAmount, LocalDateTime.now());
        this.voucherType = VoucherType.FIXED;
    }
    public FixedAmountVoucher(UUID id, Long discountAmount, LocalDateTime expirationDate){
        super(id, discountAmount, LocalDateTime.now(), expirationDate);
        this.voucherType = VoucherType.FIXED;
    }

    public FixedAmountVoucher(UUID id, Long discountAmount, LocalDateTime createdAt, LocalDateTime expirationDate){
        super(id, discountAmount, createdAt, expirationDate);
        this.voucherType = VoucherType.FIXED;
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
