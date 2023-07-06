package com.prgrms.model.dto;

import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.VoucherType;

public class VoucherRequest {

    private final VoucherType voucherType;
    private final Discount discount;

    public VoucherRequest(VoucherType voucherType, Discount discount) {
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Discount getDiscount() {
        return discount;
    }
}
