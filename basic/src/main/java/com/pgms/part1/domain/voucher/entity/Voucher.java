package com.pgms.part1.domain.voucher.entity;

import java.util.UUID;

public abstract class Voucher {
    private UUID id;
    private Integer discount;
    private VoucherDiscountType voucherDiscountType;

    protected Voucher(Integer discount, VoucherDiscountType voucherDiscountType) {
        this.id = UUID.randomUUID();
        this.discount = discount;
        this.voucherDiscountType = voucherDiscountType;
    }

    public static FixedAmountDiscountVoucher newFixedAmountDiscountVoucher(Integer discount){
        return new FixedAmountDiscountVoucher(discount);
    }

    public static PercentDiscountVoucher newPercentDiscountVoucher(Integer discount){
        return new PercentDiscountVoucher(discount);
    }

    public UUID getId() {
        return id;
    }
}
