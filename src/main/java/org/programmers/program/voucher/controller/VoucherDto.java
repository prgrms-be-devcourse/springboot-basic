package org.programmers.program.voucher.controller;

import org.programmers.program.voucher.model.FixedAmountVoucher;
import org.programmers.program.voucher.model.PercentDiscountVoucher;
import org.programmers.program.voucher.model.Voucher;
import org.programmers.program.voucher.model.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private final UUID id;
    private final VoucherType voucherType;
    private final long discountAmount;
    private final LocalDateTime expirationDate;

    public VoucherDto(){
        id = UUID.randomUUID();
        voucherType = VoucherType.FIXED;
        discountAmount = 15;
        expirationDate = LocalDateTime.now().plusWeeks(1);
    }

    public VoucherDto(VoucherType voucherType, long discountAmount, LocalDateTime expirationDate) {
        this.id = UUID.randomUUID();
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
        this.expirationDate = expirationDate;
    }

    public VoucherDto(VoucherType voucherType, long discountAmount) {
        this.id = UUID.randomUUID();
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
        this.expirationDate = LocalDateTime.now().plusWeeks(1);
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public Voucher to(){
        if (voucherType.equals(VoucherType.FIXED))
            return new FixedAmountVoucher(this);
        else
            return new PercentDiscountVoucher(this);
    }
}
