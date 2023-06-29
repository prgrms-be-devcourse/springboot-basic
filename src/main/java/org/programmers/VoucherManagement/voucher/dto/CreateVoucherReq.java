package org.programmers.VoucherManagement.voucher.dto;

import org.programmers.VoucherManagement.voucher.domain.DiscountType;

public class CreateVoucherReq {

    private final DiscountType discountType;
    private final int discountValue;

    public CreateVoucherReq(DiscountType discountType, int discountValue) {
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }
}
