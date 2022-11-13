package org.prgrms.springorder.request;

import org.prgrms.springorder.domain.VoucherType;

public class VoucherCreateRequest {

    private final VoucherType voucherType;

    private final long discountAmount;

    private VoucherCreateRequest(VoucherType voucherType, long discountAmount) {
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public static VoucherCreateRequest of(VoucherType voucherType, long discountAmount) {
        return new VoucherCreateRequest(voucherType, discountAmount);
    }

}
