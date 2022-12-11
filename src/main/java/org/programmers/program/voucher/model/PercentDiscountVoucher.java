package org.programmers.program.voucher.model;


import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher{
    public PercentDiscountVoucher(UUID id, Long discountAmount){
        super(id, discountAmount, LocalDateTime.now());
        this.voucherType = VoucherType.PERCENT;
    }
    public PercentDiscountVoucher(UUID id, Long discountAmount, LocalDateTime expirationDate){
        super(id, discountAmount, LocalDateTime.now(),expirationDate);
        this.voucherType = VoucherType.PERCENT;

    }

    public PercentDiscountVoucher(UUID id, Long discountAmount, LocalDateTime createdAt, LocalDateTime expirationDate){
        super(id, discountAmount, createdAt, expirationDate);
        this.voucherType = VoucherType.FIXED;
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
