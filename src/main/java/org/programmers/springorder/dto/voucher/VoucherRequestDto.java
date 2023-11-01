package org.programmers.springorder.dto.voucher;

import org.programmers.springorder.model.voucher.VoucherType;

public class VoucherRequestDto {
    private final long discountValue;
    private final VoucherType voucherType;

    public VoucherRequestDto(long discountValue, VoucherType voucherType) {
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
