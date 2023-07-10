package com.prgmrs.voucher.dto;

import com.prgmrs.voucher.enums.VoucherType;

public class VoucherRequest {
    private final VoucherType voucherType;
    private final String token;

    public VoucherRequest(VoucherType voucherType, String token) {
        this.voucherType = voucherType;
        this.token = token;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public String getToken() {
        return token;
    }
}
