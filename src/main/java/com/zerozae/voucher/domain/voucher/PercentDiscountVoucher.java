package com.zerozae.voucher.domain.voucher;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discount;
    private final VoucherType voucherType;
    private UseStatusType useStatusType;

    public PercentDiscountVoucher(Long discount) {
        this.voucherId = UUID.randomUUID();
        this.discount = discount;
        this.voucherType = VoucherType.PERCENT;
        this.useStatusType = UseStatusType.AVAILABLE;
    }

    public PercentDiscountVoucher(UUID voucherId, Long discount, UseStatusType useStatusType){
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = VoucherType.PERCENT;
        this.useStatusType = useStatusType;
    }
}
