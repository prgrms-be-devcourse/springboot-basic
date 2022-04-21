package kdt.vouchermanagement.domain.voucher.dto;

import kdt.vouchermanagement.domain.voucher.domain.VoucherType;

public class VoucherRequest {

    private final VoucherType voucherType;
    private final long discountValue;

    public VoucherRequest(VoucherType voucherType, long discountValue) {
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Long getDiscountValue() {
        return discountValue;
    }
}
