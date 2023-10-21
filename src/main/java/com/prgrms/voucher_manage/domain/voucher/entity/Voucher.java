package com.prgrms.voucher_manage.domain.voucher.entity;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Voucher {
    private final UUID voucherId;
    private final Long discountAmount;
    private final VoucherType voucherType;

    public Voucher(Long discountAmount, VoucherType voucherType){
        voucherId = UUID.randomUUID();
        this.discountAmount = discountAmount;
        this.voucherType = voucherType;
    }
}
