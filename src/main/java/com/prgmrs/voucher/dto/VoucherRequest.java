package com.prgmrs.voucher.dto;

import com.prgmrs.voucher.model.vo.DiscountValue;
import com.prgmrs.voucher.enums.VoucherType;

public class VoucherRequest {
    private final VoucherType voucherType;
    private final DiscountValue discountValue;

    public VoucherRequest(VoucherType voucherType, DiscountValue discountValue) {
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public DiscountValue getValue() {
        return discountValue;
    }
}
