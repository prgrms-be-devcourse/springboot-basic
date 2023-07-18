package org.programmers.VoucherManagement.voucher.dto.request;

public class VoucherUpdateRequest {
    private final int discountValue;

    public VoucherUpdateRequest(int discountValue) {
        this.discountValue = discountValue;
    }

    public int getDiscountValue() {
        return discountValue;
    }
}
