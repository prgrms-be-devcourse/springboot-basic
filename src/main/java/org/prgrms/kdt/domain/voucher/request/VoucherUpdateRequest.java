package org.prgrms.kdt.domain.voucher.request;

import org.prgrms.kdt.domain.voucher.model.VoucherType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class VoucherUpdateRequest {
    @NotNull
    private VoucherType voucherType;
    @NotNull
    @Positive
    private long discountValue;

    public VoucherUpdateRequest() {
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public void setDiscountValue(long discountValue) {
        this.discountValue = discountValue;
    }
}
