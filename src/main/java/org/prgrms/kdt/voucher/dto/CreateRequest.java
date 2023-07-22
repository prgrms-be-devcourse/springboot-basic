package org.prgrms.kdt.voucher.dto;

import org.prgrms.kdt.voucher.domain.VoucherType;

public class CreateRequest {
    private final VoucherType voucherType;
    private final double discountAmount;

    public CreateRequest(VoucherType voucherType, double discountAmount) {
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}
