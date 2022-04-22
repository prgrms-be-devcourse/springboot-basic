package kdt.vouchermanagement.domain.voucher.dto;

import kdt.vouchermanagement.domain.voucher.domain.VoucherType;

public class VoucherRequest {

    private final VoucherType voucherType;
    private final int discountValue;

    public VoucherRequest(VoucherType voucherType, int discountValue) {
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public Voucher toDomain() {
        return null;
    }
}
