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

    public static Voucher newVocher(Long id, Integer discount, VoucherDiscountType voucherDiscountType){
        if(voucherDiscountType.equals(VoucherDiscountType.PERCENT_DISCOUNT)){
            return new PercentDiscountVoucher(id, discount);
        }
        else if(voucherDiscountType.equals(VoucherDiscountType.FIXED_AMOUNT_DISCOUNT)){
            return new FixedAmountDiscountVoucher(id, discount);
        }
        else throw new RuntimeException("Voucher Type Error!!");
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
