package com.prgmrs.voucher.dto.request;

import com.prgmrs.voucher.enums.VoucherSelectionType;

public class VoucherRequest {
    private final VoucherSelectionType voucherSelectionType;
    private final String token;

    public VoucherRequest(VoucherSelectionType voucherSelectionType, String token) {
        this.voucherSelectionType = voucherSelectionType;
        this.token = token;
    }

    public VoucherSelectionType getVoucherType() {
        return voucherSelectionType;
    }

    public String getToken() {
        return token;
    }
}
