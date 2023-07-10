package org.programmers.VoucherManagement.voucher.dto;

public class UpdateVoucherRequest {
    private final int discountValue;

    public UpdateVoucherRequest(int discountValue) {
        this.discountValue = discountValue;
    }

    public int getDiscountValue() {
        return discountValue;
    }
}
