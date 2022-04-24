package org.prgrms.kdt.domain.voucher.dto;

import org.prgrms.kdt.domain.voucher.model.VoucherType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class VoucherCreateRequest {
    @NotNull
    private VoucherType voucherType;
    @NotNull
    @Positive
    private long discountValue;

    public VoucherCreateRequest(String voucherType, long discountValue) {
        this.voucherType = VoucherType.findVoucherType(voucherType);
        this.discountValue = discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscountValue() {
        return discountValue;
    }
}
