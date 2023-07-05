package com.ray.junho.voucher.controller.dto;

import com.ray.junho.voucher.domain.VoucherType;

public class VoucherResponse {

    private final long id;
    private final VoucherType voucherType;
    private final int discountValue;

    public VoucherResponse(long id, VoucherType voucherType, int discountValue) {
        this.id = id;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public long getId() {
        return id;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public String toString() {
        return "VoucherResponse{" +
                "id=" + id +
                ", voucherType=" + voucherType +
                ", discountValue=" + discountValue +
                '}';
    }
}
