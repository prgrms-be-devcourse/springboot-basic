package com.ray.junho.voucher.controller.dto;

import com.ray.junho.voucher.domain.VoucherType;

public class VoucherRequest {

    private final VoucherType voucherType;
    private final int discountValue;
    private final int amount;

    public VoucherRequest(VoucherType voucherType, int discountValue, int amount) {
        this.voucherType = voucherType;
        this.discountValue = discountValue;
        this.amount = amount;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getAmount() {
        return amount;
    }
}
