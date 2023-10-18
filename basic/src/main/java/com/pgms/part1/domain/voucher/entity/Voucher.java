package com.pgms.part1.domain.voucher.entity;

import java.util.UUID;

public abstract class Voucher {
    private UUID id;
    private Integer discount;
    private VoucherDiscountType voucherDiscountType;

    public Voucher(UUID id, Integer discount, VoucherDiscountType voucherDiscountType) {
        this.id = id;
        this.discount = discount;
        this.voucherDiscountType = voucherDiscountType;
    }

    public FixedAmountDiscountVoucher getFixedAmountDiscountVoucher(UUID id, Integer discount){
        return new FixedAmountDiscountVoucher(id, discount);
    }

    public PercentDiscountVoucher getPercentDiscountVoucher(UUID id, Integer discount){
        return new PercentDiscountVoucher(id, discount);
    }
}
