package org.devcourse.voucher.application.voucher.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.devcourse.voucher.application.voucher.model.VoucherType;

public class VoucherRequest {

    private final VoucherType voucherType;

    private final long discount;

    public VoucherRequest(@JsonProperty("voucherType") VoucherType voucherType,
                          @JsonProperty("discount") long discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscount() {
        return discount;
    }
}
