package com.example.springbootbasic.domain.voucher;

public abstract class Voucher {

    private Long voucherId;
    private Long discountValue;
    private VoucherType voucherType;

    public Voucher(Long voucherId, Long discountValue, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public Long getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() { return voucherType; }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherId=" + voucherId +
                ", discountValue=" + discountValue +
                ", voucherType=" + voucherType +
                '}';
    }
}
