package org.promgrammers.springbootbasic.dto.request;

import org.promgrammers.springbootbasic.domain.VoucherType;

public record CreateVoucherRequest(VoucherType voucherType, long discountAmount) {

    public static CreateVoucherRequest of(VoucherType voucherType, long discountAmount) {
        return new CreateVoucherRequest(voucherType, discountAmount);
    }

    @Override
    public VoucherType voucherType() {
        return voucherType;
    }

    @Override
    public long discountAmount() {
        return discountAmount;
    }
}
