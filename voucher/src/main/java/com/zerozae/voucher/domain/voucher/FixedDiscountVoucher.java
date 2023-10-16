package com.zerozae.voucher.domain.voucher;

import lombok.Getter;

import java.util.UUID;

@Getter
public class FixedDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long discount;
    private final VoucherType voucherType;
    private UseStatusType useStatusType;

    public FixedDiscountVoucher(Long discount) {
        this.voucherId = UUID.randomUUID();
        this.discount = discount;
        this.voucherType = VoucherType.FIXED;
        this.useStatusType = UseStatusType.AVAILABLE;
    }

    public FixedDiscountVoucher(UUID voucherId, long discount, UseStatusType useStatusType){
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = VoucherType.FIXED;
        this.useStatusType = useStatusType;
    }
}
