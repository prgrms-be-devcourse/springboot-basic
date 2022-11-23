package org.programmers.program.voucher.model;

import org.programmers.program.voucher.controller.VoucherDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher{
    public FixedAmountVoucher(VoucherDto dto) {
        super(dto);
        this.voucherType = VoucherType.FIXED;
    }
    public FixedAmountVoucher(UUID id, Long discountAmount){
        super(id, discountAmount);
        this.voucherType = VoucherType.FIXED;
    }
    public FixedAmountVoucher(UUID id, Long discountAmount, LocalDateTime expirationDate){
        super(id, discountAmount, expirationDate);
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
