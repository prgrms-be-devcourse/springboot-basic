package com.example.voucher.controller.request;

import com.example.voucher.constant.VoucherServiceType;
import com.example.voucher.domain.Voucher;

public class VoucherRequest {

    private final VoucherServiceType voucherServiceType;

    private Voucher.Type voucherType;

    private Long discountValue;

    public VoucherRequest(VoucherServiceType voucherServiceType) {
        this.voucherServiceType = voucherServiceType;
        this.voucherType = null;
        this.discountValue = null;
    }

    public VoucherServiceType getVoucherServiceType() {
        return voucherServiceType;
    }

    public Voucher.Type getVoucherType() {
        return voucherType;
    }

    public Long getDiscountValue() {
        return discountValue;
    }

    public void setVoucherCreateInfo(Voucher.Type voucherType, Long discountValue) {
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

}
