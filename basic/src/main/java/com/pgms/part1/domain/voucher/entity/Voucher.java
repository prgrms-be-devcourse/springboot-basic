package com.pgms.part1.domain.voucher.entity;

import java.util.UUID;

public abstract class Voucher {
    private UUID id;
    private Integer discount;
    private VoucherDiscountType voucherDiscountType;

    protected Voucher(UUID id, Integer discount, VoucherDiscountType voucherDiscountType) {
        this.id = id;
        this.discount = discount;
        this.voucherDiscountType = voucherDiscountType;
    }

    public static FixedAmountDiscountVoucher newFixedAmountDiscountVoucher(UUID id, Integer discount){
        return new FixedAmountDiscountVoucher(id, discount);
    }

    public static FixedAmountDiscountVoucher newFixedAmountDiscountVoucher(Integer discount){
        return new FixedAmountDiscountVoucher(UUID.randomUUID(), discount);
    }

    public static PercentDiscountVoucher newPercentDiscountVoucher(UUID id, Integer discount){
        return new PercentDiscountVoucher(id, discount);
    }

    public static PercentDiscountVoucher newPercentDiscountVoucher(Integer discount){
        return new PercentDiscountVoucher(UUID.randomUUID(), discount);
    }

    public UUID getId() {
        return id;
    }

    public Integer getDiscount() {
        return discount;
    }

    public VoucherDiscountType getVoucherDiscountType() {
        return voucherDiscountType;
    }
}
