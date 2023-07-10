package com.prgmrs.voucher.dto;

import com.prgmrs.voucher.model.Voucher;

public class VoucherResponse {
    private final Voucher voucher;

    public VoucherResponse(Voucher voucher) {
        this.voucher = voucher;
    }

    public Voucher getVoucher() {
        return voucher;
    }
}
