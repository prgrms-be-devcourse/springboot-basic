package org.programmers.VoucherManagement.voucher.dto.request;

import org.programmers.VoucherManagement.voucher.domain.DiscountType;

public class VoucherCreateRequest {

    private final DiscountType discountType;
    private final int discountValue;

    public VoucherCreateRequest(DiscountType discountType, int discountValue) {
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
