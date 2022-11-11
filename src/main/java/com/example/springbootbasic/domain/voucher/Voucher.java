package com.example.springbootbasic.domain.voucher;

public abstract class Voucher {
    Long voucherId;
    Long discountValue;

    public Voucher(Long voucherId, Long discountValue) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public Long getDiscountValue() {
        return discountValue;
    }

    public abstract VoucherType getVoucherType();
}
