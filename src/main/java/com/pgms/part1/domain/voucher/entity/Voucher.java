package com.pgms.part1.domain.voucher.entity;

public abstract class Voucher {
    private Long id;
    private Integer discount;
    private VoucherDiscountType voucherDiscountType;

    protected Voucher(Long id, Integer discount, VoucherDiscountType voucherDiscountType) {
        this.id = id;
        this.discount = discount;
        this.voucherDiscountType = voucherDiscountType;
    }

    public static FixedAmountDiscountVoucher newFixedAmountDiscountVoucher(Long id, Integer discount){
        return new FixedAmountDiscountVoucher(id, discount);
    }

    public static PercentDiscountVoucher newPercentDiscountVoucher(Long id, Integer discount){
        return new PercentDiscountVoucher(id, discount);
    }

    public Long getId() {
        return id;
    }

    public Integer getDiscount() {
        return discount;
    }

    public VoucherDiscountType getVoucherDiscountType() {
        return voucherDiscountType;
    }
}
